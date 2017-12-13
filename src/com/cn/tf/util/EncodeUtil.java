
package com.cn.tf.util;

import java.security.InvalidParameterException;

import org.apache.commons.codec.binary.Hex;



/**    
 * @author：张京    
 * @since：2013-3-28 下午4:46:54 
 * @version:   
 */
public final class EncodeUtil {
	/**
	 * BCD码转为10进制串,适用于左靠BCD
	 * @param bytes BCD形式的bytes
	 * @param deleteflag 转换成string后的长度是否为偶数
	 * @param bcdType 填充方式
	 * @return
	 */
	public static String bcd2String(byte[] bytes, boolean deleteflag,BCDEncodeType bcdType) {
		StringBuffer sb = new StringBuffer(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			sb.append((byte) ((bytes[i] & 0xf0) >>> 4))
			.append((byte) (bytes[i] & 0x0f));
		}
		if(bcdType==BCDEncodeType.right){
			return deleteflag?sb.substring(1):sb.toString();
		}else{
			//默认情况下为左靠
			return deleteflag?sb.substring(0,bytes.length * 2-1):sb.toString();
		}
	}


	/**
	 * 10进制转为BCD码,适用于左靠BCD的编码方式
	 * @param str
	 * @param bcdType 填充方式
	 * @return
	 */
	public static byte[] string2Bcd(String str,BCDEncodeType bcdType) {
		String s = str.toUpperCase();
		if(s.length()%2 !=0){
			if(bcdType==BCDEncodeType.right){
				s = "0"+s;
			}else{
			//左靠，右补0,默认
				s = s+"0";
			}
		}
		byte[] buffer = new byte[s.length() / 2];
		for (int i = 0; i < buffer.length; i++) {
			int i1, i2;
			char c1, c2;
			c1 = s.charAt(2*i);
			if (c1 - 'A' >= 0){
				i1 = c1 - 'A' + 10;
			}else{
				i1 = c1 - '0';
			}
			c2 = s.charAt(2*i + 1);
			if (c2 - 'A' >= 0){
				i2 = c2 - 'A' + 10;
			}else{
				i2 = c2 - '0';
			}
			buffer[i] = (byte) (i1 * 0x10 + i2);
		}
		return buffer;
	}
	/**  把16进制字符串转换成字节数组 
	    * @param hex 
	    * @return 
	    */ 
	public static byte[] hexStringToByte(String hex) { 
	    int len = (hex.length() / 2); 
	    byte[] result = new byte[len]; 
	    char[] achar = hex.toCharArray(); 
	    for (int i = 0; i < len; i++) { 
	     int pos = i * 2; 
	     result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1])); 
	    } 
	    return result; 
	} 
	private static byte toByte(char c) { 
	    byte b = (byte) "0123456789ABCDEF".indexOf(c); 
	    return b; 
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
	
	/**
	 * 十六进制字节数组转二进制字节
	 * @param b
	 * @return
	 */
	public static byte[] hex2byte(byte[] b) {
		if ((b.length % 2) != 0)
			throw new IllegalArgumentException("长度不是偶数");
		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);
			// 两位一组，表示一个字节，把这样表示的16进制字符串，还原成一个二进制字节
			b2[n / 2] = (byte) Integer.parseInt(item, 16);
		}
		return b2;
	}
	
	/**
	 * 十六进制字节数组转字符串
	 * @param b
	 * @return
	 */
	public static String hex2String(byte[] b) {
	
		
			String item = new String(b, 0, b.length);
			// 两位一组，表示一个字节，把这样表示的16进制字符串，还原成一个二进制字节
			
		
		return item;
	}
	
	/**
	    * 把字符串转换为16进制ascii码
	    * @param bArray 
	    * @return 
	    */ 
	public static  byte[] StringToHexAscii(String str) { 
	    byte[]  b = new byte[str.length()];
	    for (int i = 0; i < str.length(); i++) { 
	    	b[i]=(byte)(0xFF & str.charAt(i)); 
	    }
	    return b; 
	} 
	/**
	 * 字符串按给定长度补0
	 * @param str
	 * @param len
	 * @return
	 */
	   public static String StringFillLeftZero(String str, int len) {
		    if (str.length() < len) {
		      StringBuffer sb = new StringBuffer(len);
		      for (int i=0; i<len - str.length(); i++)
		        sb.append('0');
		      sb.append(str);
		      return new String(sb);
		    }
		    else
		      return str;
		  }
	   /**
		 * 解析交易金额到指定格式，(如:1000.02 -> 000000100002)
		 * @param amount 金额
		 * @return
		 */
		public static String parseAmount(Double amount) {
			String s = String.valueOf((int)(amount * 100));
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < 12 - s.length(); i++) {
				sb.append("0");
			}
		return sb.append(s).toString();
	}
		
		public static byte[] ascStr2Bcd(String s)
		{
			return ascii2Bcd(str2Ascii(s));
		}
		
		public static byte[] str2Ascii(String s)
		{
			byte[] str = s.toUpperCase().getBytes();
			byte[] ascii = new byte[str.length];
			for (int i = 0; i < ascii.length; i++)
			{
				ascii[i] = (byte) asciiValue(str[i]);
			}
			return ascii;
		}
		private static int asciiValue(byte b)
		{
			if ((b >= '0') && (b <= '9'))
			{
				return (b - '0');
			}
			if ((b >= 'a') && (b <= 'f'))
			{
				return (b - 'a') + 0x0a;
			}
			if ((b >= 'A') && (b <= 'F'))
			{
				return (b - 'A') + 0x0a;
			}

			throw new InvalidParameterException();
		}
		public static byte[] ascii2Bcd(byte[] asc)
		{
			int len = asc.length / 2;
			byte[] bcd = new byte[len];
			for (int i = 0; i < len; i++)
			{
				bcd[i] = (byte) ((asc[2 * i] << 4) | asc[2 * i + 1]);
			}
			return bcd;
		}
    //字符串转换为16进制字符串
    public static String stringToHexString(String s) {  
        String str = "";  
        for (int i = 0; i < s.length(); i++) {  
            int ch = (int) s.charAt(i);  
            String s4 = Integer.toHexString(ch);  
            str = str + s4;  
        }  
        return str;  
    }  
	public static void main(String[] args) {
		String str="0200702006c020c19a11166225000000000261600000000000002000648816051000209112286225000000000261d301020100005a463131303139353330333131303333373030313130310003514323313536c271b1f4936b9495100000000000000001029f2608b58e85ff86b6fe879f2701809f101307000103a00000010a01000005000058609ee09f3704fc1ec5379f360200d7950580800400009a031309269c01609f02060000000020005f2a02015682027c009f1a0201569f03060000000000009f330360400000160013092600000050a130506ecf5e16b0";
		byte[] b = hexStringToByte(str);
		System.out.println(new String ( bytesToHexString( str.getBytes() )));
		
		Hex hex = new Hex();
		
		try {
			System.out.println(bytesToHexString(  "212".getBytes() )  );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
	}	
}
