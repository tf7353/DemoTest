package com.cn.tf.testextends;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
public class TestExtends {
	@Before
	public void before(){
		System.out.println("before");
	}
	@Test
	public void test(){
		
		TestService t = new TestService();
		t.method2();
		/*总结:一个实体继承另一个实体,那么方法调用时传的参数可以用父类接,多态的思想*/
	}
	@After
	public void after(){
	System.out.println("after");	
	}
}


