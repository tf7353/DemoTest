package com.cn.tf.bcd;

import java.nio.charset.Charset;

import com.cn.tf.util.EncodeUtil;
import org.junit.Test;

public class BCDdemo {
	@Test
	public void test (){
		//bcd压缩成字节数组
		byte[] aa= str2Bcd("6001510000603100310000");
		System.out.println(aa);
		//字节数组转成16进制字符串EncodeUtil.bytesToHexString(aa)
		//bcd压缩后产生的字节数组如果换成16进制的字节数组然后转为字符串貌似就变成跟原来长得一样了，也不完全是一样，经验证偶尔还是不一样的
		System.out.println(EncodeUtil.bytesToHexString(aa));
		
		System.out.println(new String(aa));
		System.out.println(aa.toString());
	}
	private static byte[] str2Bcd(String asc) {
	    int len = asc.length();
	    len /= 2;

	    byte[] bbt = new byte[len];
	    byte[] abt = asc.getBytes(Charset.forName("utf-8"));

	    for (int p = 0; p < len; ++p) {
	        int j; //字节高4位
	        int k; //字节低4位

	        if ((abt[(2 * p)] >= 97) && (abt[(2 * p)] <= 122)) {
	            //字符a-z
	            j = abt[(2 * p)] - 97 + 10;
	        }
	        else if ((abt[(2 * p)] >= 65) && (abt[(2 * p)] <= 90)) {
	            //字符A-Z
	            j = abt[(2 * p)] - 65 + 10;
	        }
	        else {
	            //数字0-9
	            j = abt[(2 * p)] - 48;
	        }

	        if ((abt[(2 * p + 1)] >= 97) && (abt[(2 * p + 1)] <= 122)) {
	            k = abt[(2 * p + 1)] - 97 + 10;
	        }
	        else if ((abt[(2 * p + 1)] >= 65) && (abt[(2 * p + 1)] <= 90)) {
	            k = abt[(2 * p + 1)] - 65 + 10;
	        }
	        else {
	            k = abt[(2 * p + 1)] - 48;
	        }

	        int a = (j << 4) + k;
	        byte b = (byte) a;
	        bbt[p] = b;
	    }
	    return bbt;
	}
}


