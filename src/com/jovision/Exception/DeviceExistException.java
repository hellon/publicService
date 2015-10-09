package com.jovision.Exception;

public class DeviceExistException extends Exception {

	/**
	 * 设备已经存在异常
	 */
	private static final long serialVersionUID = -1170925506455778297L;
	 public DeviceExistException(String msg)  
	    {  
	        super(msg);  
	    }  
}
