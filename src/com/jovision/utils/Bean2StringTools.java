package com.jovision.utils;

import java.lang.reflect.Field;

import com.jovision.domain.TbDeviceBasic;
import com.jovision.domain.TbDeviceIpc;
import com.jovision.domain.TbUserDevice;

/**
 * 对象转换为字符串的各种工具
 * @author Hellon(刘海龙)
 *
 */
public class Bean2StringTools {
	
	/**
	 * 对象转换为字符串工具
	 * @param <T>
	 * @param clazz
	 * @param tabName
	 */
	public static <T> void bean2String(Class<T> clazz,String tabName){
		Field[] fields = clazz.getDeclaredFields();
		StringBuilder sb = new StringBuilder("new map(");
		for(int i=0;i<fields.length;i++){
			if(i+1 == fields.length){
				sb.append(tabName+".").append(fields[i].getName()).append(" as ").append(fields[i].getName()).append(")");
			}else{
				sb.append(tabName+".").append(fields[i].getName()).append(" as ").append(fields[i].getName()).append(",");
			}
		}
		
		System.out.println(sb.toString());
	}
	
	public static void main(String[] args) {
		bean2String(TbUserDevice.class,"tud");
		//bean2String(TbDeviceIpc.class,"tdi");
	}
}
