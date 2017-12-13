package com.cn.tf.testextends;


public class TestService {
	public void method1(People people){
		//参数people实际上还是sutdent,多态思想,people的属性为sutdent的属性集合
		System.out.println(people.toString());
	}
	public void method2(){
		Sutdent s = new Sutdent();
		s.setAge("1");
		s.setName("2");
		s.setSex("3");
		s.setIdcard("1111");
		method1(s);
	}
}


