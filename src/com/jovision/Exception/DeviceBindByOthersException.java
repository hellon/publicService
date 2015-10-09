package com.jovision.Exception;

public class DeviceBindByOthersException extends Exception {

	/**
	 * 用户设备绑定关系已经存在异常
	 */
	private static final long serialVersionUID = -1170925506455778297L;
	 public DeviceBindByOthersException(String msg)  
	    {  
	        super(msg);  
	    }  
}
