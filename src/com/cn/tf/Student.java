package com.cn.tf;

import org.junit.Test;
/**
 * 多态中方法重写后的执行过程  
 * @filename:	 com.tf.Person
 * @copyright:   Copyright (c)2010
 * @company:     北京海科融通支付股份
 * @author:      tf 
 * @version:     1.0
 * @createtime: 2017年3月23日 上午11:37:27
 * @record
 */
	class Person { 
		/**
		 * 
		 * 2017年3月23日下午3:18:51 f
		 */
	    public void say()  
	    {  
	        System.out.println("我是Person的say方法");  
	    }  
	}  
	public class Student extends Person{
	    //重写了父类的say()方法  
	    @Override  
	    public void say()  
	    {
	        System.out.println("我是Student的say方法");  
	    }
	    @Test
	    public void test(){
	    	 Student stu=new Student();  
		        stu.say();//我是Student的say方法  
		        Person p=new Student();//多态  
		        p.say();//我是Student的say方法  
	    }
	    //总结:上面的p.say()，可以理解为如果子类由say()方法就调用子类的，如果子类没有say()方法，那么就再去调用父类的say()方法.
	}  



