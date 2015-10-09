package com.jovision.Exception;

public class DeviceBindNoExistException extends Exception {

	/**
	 * 用户设备绑定关系不存在异常
	 */
	private static final long serialVersionUID = -1170925506455778297L;
	 public DeviceBindNoExistException(String msg)  
	    {  
	        super(msg);  
	    }  
}
