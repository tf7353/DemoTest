package com.cn.tf;
public class Test {
	  public static void main(String[] args)  
	    {  
	       String hexString = "31";  
//	       String ss="0000000000000000";
//	       byte[]  bb = ss.getBytes();

	       System.out.println(hexString2binaryString(hexString));  
	    }  
	  
	    public static String hexString2binaryString(String hexString)  
	    {  
	        if (hexString == null || hexString.length() % 2 != 0)  
	            return null;  
	        String bString = "", tmp;  
	        for (int i = 0; i < hexString.length(); i++)  
	        {  
	            tmp = "0000"  
	                    + Integer.toBinaryString(Integer.parseInt(hexString  
	                            .substring(i, i + 1), 16));  
	            bString += tmp.substring(tmp.length() - 4);  
	        }  
	        return bString;  
	    }  
}


