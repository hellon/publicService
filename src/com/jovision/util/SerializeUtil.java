/**
 * 
 */
package com.jovision.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.jovision.common.constProperty;
import com.jovision.domain.DeviceWhole;
import com.jovision.redisDao.redisFactory;
import com.jovision.test.Person;

/**
 * @Title: test1.java 
 * @Package com.jovision.util
 * @author Joker(张凯)
 * @Description: TODO() 
 * @date 2015-9-10 下午08:50:53
 */

public class SerializeUtil {
	
	/**
	 * 对象序列化
	 * @author Joker(张凯) Hellon(刘海龙) 
	 * @param object
	 * @return
	 */
	public static byte[] serialize(Object object) {
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		try {
			//序列化
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			byte[] bytes = baos.toByteArray();
			return bytes;
		} catch (Exception e) {
			
		}
		return null;
	}
	
	/**
	 * 对象反序列化
	 * @author Joker(张凯) Hellon(刘海龙)
	 * @param bytes
	 * @return
	 */
	public static Object unserialize(byte[] bytes) {
	ByteArrayInputStream bais = null;
	try {
		//反序列化
		bais = new ByteArrayInputStream(bytes);
		ObjectInputStream ois = new ObjectInputStream(bais);
		return ois.readObject();
	} catch (Exception e) {
	 
	}
		return null;
	}
	
	/**
	 * 从redis数据库中获取对象
	 * @author Joker(张凯) Hellon(刘海龙)
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static Object getObject(int index,String key) throws Exception {
		byte[] objSer = redisFactory.getBytes(index,key.getBytes());
		return SerializeUtil.unserialize(objSer);
	}
	
	/**
	 * 向redis数据库存放序列化后的对象
	 * @author Joker(张凯) Hellon(刘海龙) 
	 * @param key
	 * @param obj
	 * @throws Exception
	 */
	public static void setObject(int index,String key,Object obj) throws Exception {
		redisFactory.setBytes(index,key.getBytes(), SerializeUtil.serialize(obj));
		
	}
	
	public static void main(String[] args) throws Exception {
		Person per = new Person(100, "hi你你1123123");
		setObject(1,"102",per);
		Person person = (Person) getObject(1,"102");
		System.out.println(person.getId());
		System.out.println(person.getName());
		
		DeviceWhole dW = (DeviceWhole)getObject(1,constProperty.DEVICE+"zktest2");
		System.out.println(dW.getTdb().getDeviceGuid());
		
	}	

}

