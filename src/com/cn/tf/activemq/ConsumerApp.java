package com.cn.tf.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

public class ConsumerApp implements MessageListener{
	 private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerApp.class);
	 	//部署的mq地址
	    private static final String BROKER_URL = "failover://tcp://192.168.31.129:61616";
	    private static final String SUBJECT = "test-activemq-queue";

	    public static void main(String[] args) throws JMSException {
	        //初始化ConnectionFactory
	        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);

	        //创建mq连接
	        Connection conn = connectionFactory.createConnection();
	        //启动连接
	        conn.start();

	        //创建会话
	        Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);

	        //通过会话创建目标
	        Destination dest = session.createQueue(SUBJECT);
	        //创建mq消息的消费者
	        MessageConsumer consumer = session.createConsumer(dest);

	        //初始化MessageListener
	        ConsumerApp me = new ConsumerApp();

	        //给消费者设定监听对象
	        consumer.setMessageListener(me);
	    }

		public void onMessage(Message message) {
			  TextMessage txtMessage = (TextMessage)message;
			  Long starttime=System.currentTimeMillis();
				System.out.println("开始时间:"+starttime);
			  try {
				    Thread.sleep(1000);                 //1000 毫秒，也就是1秒.
				} catch(InterruptedException ex) {
				    Thread.currentThread().interrupt();
				}
		        try {
		        
		            LOGGER.info ("get message " + txtMessage.getText());
		            Long endtime=System.currentTimeMillis();
		            System.out.println("结束时间:"+endtime);
		            
		            System.out.println("总时间:"+(endtime-starttime));
		        } catch (JMSException e) {
		            LOGGER.error("error {}", e);
		        }
			
		}


}


