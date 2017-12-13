package com.cn.tf;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class ByteTest {
 public static void main(String[] args) throws UnsupportedEncodingException {
 // String str = "A0 Hello world!你好";    0011 0100 0101
  String str = "    "; //不同的编码方式对数字字符和字母字符的编码是一样的,转换为字节数组是一样的
  // string转byte
  byte[] bs = str.getBytes();  //[65, 48, 32, 72, 101, 108, 108, 111, 32, 119, 111, 114, 108, 100, 33, -28, -67, -96, -27, -91, -67]
  byte[] bs1 = str.getBytes("UTF-8");//[65, 48, 32, 72, 101, 108, 108, 111, 32, 119, 111, 114, 108, 100, 33, -28, -67, -96, -27, -91, -67]
  byte[] bs2 = str.getBytes("GBK");//[65, 48, 32, 72, 101, 108, 108, 111, 32, 119, 111, 114, 108, 100, 33, -60, -29, -70, -61]
  byte[] bs3 = str.getBytes("ASCII");//[65, 48, 32, 72, 101, 108, 108, 111, 32, 119, 111, 114, 108, 100, 33, 63, 63]
  System.out.println(Arrays.toString(bs));
  System.out.println(Arrays.toString(bs1));
  System.out.println(Arrays.toString(bs2));
  System.out.println(Arrays.toString(bs3));
  System.out.println(bytesToHexString(bs3));
  // byte转string
  String str2 = new String(bs);
  System.out.println(str2);
 }
 /**
  * 把字节数组转换成16进制字符串 
  * @param bArray 
  * @return 
  */ 
 public static  String bytesToHexString(byte[] bArray) { 
	    StringBuffer sb = new StringBuffer(bArray.length); 
	    String sTemp; 
	    for (int i = 0; i < bArray.length; i++) { 
	     sTemp = Integer.toHexString(0xFF & bArray[i]); 
	     if (sTemp.length() < 2) 
	      sb.append(0); 
	     sb.append(sTemp.toUpperCase()); 
	    } 
	    return sb.toString(); 
	} 
 // A 的 ASCII 码为 65 ， 0 的 ASCII 码为 48 
}
