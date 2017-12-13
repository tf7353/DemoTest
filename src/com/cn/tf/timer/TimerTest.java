package com.cn.tf.timer;

import org.junit.Test;

import java.util.Timer;
import java.util.TimerTask;

public class TimerTest {
	@Test//用junit启动不生效
	public void test1(){
	/*	Timer timer = new Timer();
		timer.schedule(new TimerTask() {
		        public void run() {
		            System.out.println("11232");
		        }
		},2000,1000);//2秒后开始执行,每1秒执行一次
*/	}
	//用main方法启动
	public static void main(String[] args) {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
		        public void run() {
		            System.out.println("11232");
		        }
		},2000,1000);//2秒后开始执行,每1秒执行一次
	}
}


