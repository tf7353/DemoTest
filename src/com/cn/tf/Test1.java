package com.cn.tf;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import com.cn.tf.util.EncodeUtil;
import org.apache.commons.httpclient.util.DateUtil;
import org.junit.Test;

/**
 * 
 * @filename:	 Test1
 * @copyright:   Copyright (c)2010
 * @company:     北京海科融通支付股份
 * @author:      tf 
 * @version:     1.0
 * @createtime: 2017年8月23日 上午10:50:51
 * @record
 */
public class Test1 {

	
	@Test
	public void gettime() {
		System.out.println(DateUtil.formatDate(new Date(), "yyyyMMddHHmmss"));
		
	}
	private  int parseType(String type) throws IOException {
		if (type.length() % 2 == 1) {
			type = "0" + type;
		}
		if (type.length() != 4) {
			throw new IOException("Invalid type for template: " + type);
		}
		return ((type.charAt(0) - '0') << 12) | ((type.charAt(1) - '0') << 8)
				| ((type.charAt(2) - '0') << 4) | (type.charAt(3) - '0');
	}
	@Test
	public void test() throws IOException{
		
		int s = parseType("8583");
		System.out.println(s);
	}
	@Test
	public void test1() throws IOException{
		
		String[] mustNums = "45".split("|");
		System.out.println(mustNums.toString());
	}
	//
	@Test
	public void test2() {
		String sss ="00000";
		String s2 = EncodeUtil.stringToHexString(sss);
		System.out.println(s2);
//		byte[] b =EncodeUtil.string2Bcd(sss, null);
//		byte[] b2 = EncodeUtil.string2Bcd(s2, null);
//		String s =EncodeUtil.bytesToHexString(b);
//		String  sss2 =EncodeUtil.bytesToHexString(b2);
//		String s1 =EncodeUtil.bytesToHexString(sss.getBytes());
//		System.out.println(s);
//		System.out.println(s1);
//		System.out.println(sss2);
		
	}
	@Test
	public void test3() {
		
		HashMap<String, String> m = new HashMap<String, String>();
		m.put("aa", "124");
		m.put("a2a", "1243");
		System.out.println(m.get("aa"));
		System.out.println(m.get("aa1"));
		System.out.println(m.get(null));
		//map可以直接打印
		System.out.println(m);
		System.out.println(m.toString());
		
	}
	//ToStringBuilder 测试使用
	@Test
	public void test4() {
		ClassEntity ce = new ClassEntity();

		ce.setAdresss("我是地址");
		ce.setNumber(111);
		ce.setPhone("1868368833");
		System.out.println(ce);
		ce.setAdresss("我是地址");
		ce.setNumber(111);
		ce.setPhone("1868368833");
		System.out.println(ce);

	}


	@Test
	public void test5() {

		String ss = "aaa";
		String[] split = ss.split(",");
		for (String string : split) {
			System.out.println(string);
		}
	}
	@Test
		public void test6(){
		System.out.println(111);
	}
}


