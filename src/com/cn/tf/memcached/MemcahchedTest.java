package com.cn.tf.memcached;

import net.spy.memcached.MemcachedClient;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.util.concurrent.Future;

public class MemcahchedTest {
    @Test
    public  void test(){
        try{
            // 本地连接 Memcached 服务
            MemcachedClient mcc = new MemcachedClient(new InetSocketAddress("192.168.31.129", 11211));
            System.out.println("Connection to server sucessful.");

            // 关闭连接
            mcc.shutdown();

        }catch(Exception ex){
            System.out.println( ex.getMessage() );
        }
    }

    @Test
    public  void test1(){
        try{
            // 连接本地的 Memcached 服务
            MemcachedClient mcc = new MemcachedClient(new InetSocketAddress("192.168.31.129", 11211));
            System.out.println("Connection to server sucessful.");

            // 存储数据
            Future fo = mcc.set("runoob", 900, "Free Education");

            // 查看存储状态
            System.out.println("set status:" + fo.get());

            // 输出值
            System.out.println("runoob value in cache - " + mcc.get("runoob"));

            // 关闭连接
            mcc.shutdown();

        }catch(Exception ex){
            System.out.println( ex.getMessage() );
        }
    }

}
