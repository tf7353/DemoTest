张善闯:
		package com.elong.hotel.amqp.connectpool;

import java.security.InvalidParameterException;
import java.util.LinkedList;
import java.util.Queue;
import com.elong.hotel.amqp.Const.Const;
import com.elong.hotel.amqp.config.RabbitConfig;
import com.elong.hotel.amqp.exception.AmqpClientException;
import com.elong.hotel.amqp.util.exceptionHelper.ExceptionLogHelper;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;


/**
 * Rabbit客户端连接池,统一由该类创建接收和发送的连接
 *
 * @author heshixiong
 */
public class RabbitConnectPool implements DisposeListener {

	private static Logger logger = Logger.getLogger(Const.JAMQP_LOGGER_CLIENT);
	/**
	 * 连接池的公用信息
	 */
	private int _max = 0; // 可以创建的最大连接数

	private int _created = 0; // 已经创建的连接数
	private int _used = 0; // 已经使用了的连接数

	private int _sendTimeOut = 0; // 发送连接请求超时时间
	private int _receiveTimeOut = 0; // 接收连接成功的超时时间
	private int _clientExpires = 5000; // 连接到期时间
	private int _connectionTimeOut = 0;
	private int _qos = 1;

	private ConnectionFactory _proxyFactory = new ConnectionFactory(); // 创建连接
	private Queue<RabbitSendProxy> _sendProxyQueue = new LinkedList<>();
	//  private Map<String, RabbitReceiveProxy> _receiveProxyMap = Collections
	//      .synchronizedMap(new HashMap<String, RabbitReceiveProxy>());
	private final Object lock = new Object();
	private ConfirmCallback confirmCallback;
	private RabbitProxyDisposer sendProxyDisposer = new RabbitProxyDisposer();

	private static RabbitConnectPool singleTon = null;
	private static final Object singleInstanceLock = new Object();

	//建立连接,发送端连接指定为SEND,接收端连接指定为GET
	private final String sendIndexForConn = "SEND";
	private final String getIndexForConn = "GET";

	private RabbitConnectPool() {
	}

	/**
	 * 单例模式返回连接池实例
	 *
	 * @return RabbitConnectPool 连接池实例
	 */
	public static RabbitConnectPool getInstance() {
		if (singleTon == null) {
			synchronized (singleInstanceLock) {
				if (singleTon == null) {
					singleTon = new RabbitConnectPool();
				}
			}
		}
		return singleTon;
	}

	/**
	 * 初始化连接池
	 */
	public void Init() {
		this._max = RabbitConfig.getInstance().getMaxPoolSize();
		this._sendTimeOut = RabbitConfig.getInstance().getSendTimeOut();
		this._receiveTimeOut = RabbitConfig.getInstance().getReceiveTimeOut();
		this._connectionTimeOut = RabbitConfig.getInstance().getConnectionTimeOut();
		if (RabbitConfig.getInstance().getQos() > 0) {
			this._qos = RabbitConfig.getInstance().getQos();
		}
		this._proxyFactory.setHost(RabbitConfig.getInstance().getHostName());
		this._proxyFactory.setPort(RabbitConfig.getInstance().getPort());
		this._proxyFactory.setPassword(RabbitConfig.getInstance().getPassWord());
		this._proxyFactory.setUsername(RabbitConfig.getInstance().getUserName());
		this._proxyFactory.setConnectionTimeout(_clientExpires);
		this._proxyFactory.setRequestedHeartbeat(RabbitConfig.getInstance().getRequestHeartBeat());
	}

	public int getMax() {
		return this._max;
	}

	public int getCreated() {
		return this._created;
	}

	public int getExpireTime() {
		return this._clientExpires;
	}

	public int getUsed() {
		return this._used;
	}

	public synchronized void setConfirmCallback(ConfirmCallback callback) {
		long beginTime=System.currentTimeMillis();
		if (this.confirmCallback == null || this.confirmCallback == callback) { // 暂时仅实现支持一个callback
			confirmCallback = callback;
		} else {
			String errMsg="Only one confirm callback supported.";
			logger.error(errMsg);
			ExceptionLogHelper.BussinessExceptionProcess(errMsg, "ConfirmCallBackError", beginTime,
					Const.ACTIONLOG_PRODUCTLINE, "ConfirmCallBackSetError");
			throw new InvalidParameterException(errMsg);
		}
	}

	/**
	 * 创建发送端的连接池
	 *
	 * @param forceNew 强制建立连接
	 * @return 返回创建的连接
	 * @throws AmqpClientException
	 */
	public RabbitSendProxy getSendingConnection(boolean forceNew) throws AmqpClientException {
		long beginTime=System.currentTimeMillis();
		if (!forceNew) {
			RabbitProxy proxy = getNextProxy();
			if (proxy != null) {
				return (RabbitSendProxy) proxy;
			} else {
				String errMsg="Unexpected proxy creation";
				logger.error(errMsg);
				ExceptionLogHelper.BussinessExceptionProcess(errMsg,"CastError",beginTime,Const.ACTIONLOG_PRODUCTLINE,"CastError");
				throw new AmqpClientException(errMsg);
			}
		} else {
			// 强制创建一个新的连接
			return (RabbitSendProxy) getNewProxy(sendIndexForConn);
		}
	}

	/**
	 * 获取下一个连接
	 *
	 * @return RabbitProxy 返回下一个新的连接
	 * @throws AmqpClientException
	 */
	private RabbitSendProxy getNextProxy() throws AmqpClientException {
		long beginTime=System.currentTimeMillis();
		if (this._max <= 0) {
			return (RabbitSendProxy) getNewProxy(sendIndexForConn);
		}
		RabbitSendProxy proxy;
		synchronized (lock) {
			try {
				proxy = pollProxy();
				while (proxy == null) {
					lock.wait();
					proxy = pollProxy();
				}
			} catch (Exception e) {
				logger.error(ExceptionUtils.getFullStackTrace(e));
				ExceptionLogHelper.SystemExceptionProcess(e, beginTime, Const.ACTIONLOG_PRODUCTLINE, "GetProxyError");
				throw new AmqpClientException("error get connection from pool ", e);
			}
		}
		return proxy;
	}

	private RabbitSendProxy pollProxy() throws AmqpClientException { // must hold lock
		RabbitSendProxy proxy = _sendProxyQueue.poll();
		while (proxy != null && !proxy.isAvailable()) {
			this._created--;
			proxy = _sendProxyQueue.poll();
		}
		if (proxy != null) {
			this._used++;
		} else if (this._created < this._max) {
			proxy = (RabbitSendProxy) getNewProxy(sendIndexForConn);
			this._created++;
			this._used++;
		}
		return proxy;
	}

	/**
	 * 将连接放到池子中
	 *
	 * @param proxy 将要返回的连接
	 */
	public void returnToPool(RabbitSendProxy proxy) {
		long beginTime=System.currentTimeMillis();
		if (proxy != null) {
			if (this._max <= 0) {
				proxy.disPose();
			} else {
				synchronized (lock) {
					try {
						if (!proxy.isAvailable()) {
							this._created--;
							this._used--;
						} else {
							this._sendProxyQueue.add(proxy);
							this._used--;
						}
					} catch (Exception e) {
						logger.error("return proxy to pool error", e);
						ExceptionLogHelper.SystemExceptionProcess(e, beginTime, Const.ACTIONLOG_PRODUCTLINE, "ReturnPoolError");
					} finally {
						lock.notify();
					}
				}
			}
		}
	}

	/**
	 * 获取一个新建的接收端连接
	 *
	 * @param qos 指定一次消息接收数量
	 * @return 返回创建的新连接
	 * @throws AmqpClientException
	 */
	public RabbitReceiveProxy getReceiveConnection(int qos)
			throws AmqpClientException {
		RabbitReceiveProxy proxy;
		if (qos > 0) {
			proxy = (RabbitReceiveProxy) getNewProxy(getIndexForConn, qos);
		} else {
			proxy = (RabbitReceiveProxy) getNewProxy(getIndexForConn);
		}
		return proxy;
	}

	//  /**
	//   * 注册监听器
	//   *
	//   * @param queueName 队列名称
	//   * @param proxy 创建的代理对象
	//   */
	//  private void registerListenerProxy(String queueName, RabbitReceiveProxy proxy) {
	//    if (!_receiveProxyMap.containsKey(queueName)) {
	//      _receiveProxyMap.put(queueName, proxy);
	//    }
	//  }
	//
	//  /**
	//   * 使用完毕连接之后返回连接
	//   *
	//   * @param queueName 队列名称
	//   * @param proxy 创建的代理对象
	//   */
	//  public void returnListener(String queueName, RabbitReceiveProxy proxy) {
	//    if (proxy != null) {
	//        proxy.disPose();
	//    }
	//    _receiveProxyMap.remove(queueName);
	//  }

	@Override
	public void proxyDisposed(RabbitProxy proxy) {
		if (proxy != null && proxy instanceof RabbitSendProxy && this._max > 0) {
			synchronized (lock) {
				if (this._sendProxyQueue.remove(proxy)) {
					_created--;
				}
				lock.notify();
			}
		}
	}

	/**
	 * 获取一个新的连接,如果没有指定QOS,使用默认的qos
	 *
	 * @return RabbitProxy 返回新建的连接
	 * @throws AmqpClientException
	 */
	private RabbitProxy getNewProxy(String functionName) throws AmqpClientException {
		return getNewProxy(functionName, this._qos);
	}

	private RabbitProxy getNewProxy(String functionName, int qos) throws AmqpClientException {
		long beginTime=System.currentTimeMillis();
		for (int i = 0; i < 3; i++) {
			try {
				RabbitProxy proxy = getProxy(functionName, qos);
				if (proxy != null) {
					return proxy;
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				ExceptionLogHelper.SystemExceptionProcess(e, beginTime, Const.ACTIONLOG_PRODUCTLINE, "GetProxyError");
			}

			try {
				final int waitingTime = 100; // 等待再次创建的时间为1秒
				Thread.sleep(waitingTime);
			} catch (Exception e) {
				logger.error("sleep for waitingTime error", e);
				ExceptionLogHelper.SystemExceptionProcess(e, beginTime, Const.ACTIONLOG_PRODUCTLINE, "InterruptError");
			}
		}

		throw new AmqpClientException("Get new proxy failed");
	}


	private RabbitProxy getProxy(String functionName, int qos) throws AmqpClientException {
		RabbitProxy proxy = null;
		if (functionName.equalsIgnoreCase(sendIndexForConn)) {
			RabbitSendProxy sendProxy =
					new RabbitProxyFactory(this._proxyFactory, this._sendTimeOut, this._receiveTimeOut,
							this._connectionTimeOut, qos).getSendProxy();
			sendProxy.setConfirmCallback(confirmCallback);
			sendProxy.setDisposeListener(this);
			sendProxyDisposer.addDisposableProxy(sendProxy); // sendProxy异步关闭
			proxy = sendProxy;
		}
		if (functionName.equalsIgnoreCase(getIndexForConn)) { // receiveProxy主动关闭
			proxy =
					new RabbitProxyFactory(this._proxyFactory, this._sendTimeOut, this._receiveTimeOut,
							this._connectionTimeOut, qos).getReceiveProxy();
		}

		return proxy;
	}

}

