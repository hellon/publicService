/**
 * 
 */
package com.jovision.Exception;

/**
 * @Title: RedisExceptiom.java 
 * @Package com.jovision.Exception
 * @author Hellon(刘海龙)
 * @Description: Redis数据库出现异常() 
 * @date 2015-9-14 上午09:32:18
 */
public class RedisException extends Exception {
	
	public RedisException(String message){
		super(message);
	}
}
