package com.jovision.Exception;

public class DeviceBindSelfExistException extends Exception {

	/**
	 * 用户设备绑定关系已经存在异常
	 */
	private static final long serialVersionUID = -1170925506455778297L;
	 public DeviceBindSelfExistException(String msg)  
	    {  
	        super(msg);  
	    }  
}
