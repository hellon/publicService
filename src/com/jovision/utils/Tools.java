package com.jovision.utils;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;



public class Tools {

	/**
	 * 字符串转int
	 * 
	 * @param value
	 * @return
	 */
	public static int typeInteger(String value) {
		if (value.equals(""))
			value = "0";
		return Integer.parseInt(value);
	}

	/**
	 * 字符串转
	 * 
	 * @param value
	 * @return
	 */
	public static String typeString(String value) {
		return value;
	}

	/**
	 * 字符串转Double
	 * 
	 * @param value
	 * @return
	 */
	public static double typeDouble(String value) {
		if (value.equals(""))
			value = "0";
		return Double.parseDouble(value);
	}

	/**
	 * 字符串装Long
	 * 
	 * @param value
	 * @return
	 */
	public static long typeLong(String value) {
		if (value.equals(""))
			value = "0";
		return Long.parseLong(value);
	}

	/**
	 * 字符串转日期
	 * 
	 * @param value
	 * @return
	 */
	public static Date typeDate(String value) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		if (value.equals(""))
			value = format.format(new Date());
		try {
			return format.parse(value);
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 字符串转时间
	 * 
	 * @param value
	 * @return
	 */
	public static Timestamp typeTimestamp(String value) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (value.equals(""))
			value = format.format(new Date());
		try {
			return new Timestamp(format.parse(value).getTime());

		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 字符串转布尔
	 * 
	 * @param value
	 * @return
	 */
	public static Boolean typeBoolean(String value) {
		return value.equals("true") ? true : false;
	}

	/**
	 * 判断输入的字符串是不是为空或空字符串
	 * @author Hellon(刘海龙) 
	 * @param str
	 * @return
	 */
	public static boolean strIsNull(String str){
		if(str == null|| str!=null && str.trim().equals("")){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * @Title: Tools.java 
	 * @Package com.jovision.utils
	 * @author Joker(张凯)
	 * @Description: TODO() 
	 * @date 2015-9-21 下午01:40:27
	 * @param str
	 * @return
	 */
	public static boolean strArrIsNull(String... str){
		for(String str_new:str)
		{
			if(str_new == null|| str_new!=null && str_new.trim().equals("")){
				return true;
			}else{
				continue;
			}
		}
		return false;
	}

}
