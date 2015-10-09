package com.jovision.utils;


import java.security.MessageDigest;

public class MD5
{

    public MD5()
    {
    }

    public static String md5(String s)
    {
        char hexDigits[] = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
            'A', 'B', 'C', 'D', 'E', 'F'
        };
        char str[];
        byte strTemp[] = s.getBytes();
        try
        {
	        MessageDigest mdTemp = MessageDigest.getInstance("MD5");
	        mdTemp.update(strTemp);
	        byte md[] = mdTemp.digest();
	        int j = md.length;
	        str = new char[j * 2];
	        int k = 0;
	        for(int i = 0; i < j; i++)
	        {
	            byte byte0 = md[i];
	            str[k++] = hexDigits[byte0 >>> 4 & 0xf];
	            str[k++] = hexDigits[byte0 & 0xf];
	        }
	
	        return (new String(str)).substring(8, 24);
        }
        catch(Exception e)
        {
        	return null;
        }
    }

    public static void main(String args[]){
    	
    	MD5 MD5=new MD5();
    	System.out.println(MD5.md5("system"));
    }

}
