package com.jovision.common;

/**
 * 
 * @Title: constProperty.java 
 * @Package com.jovision.common
 * @author Joker(张凯)
 * @Description: TODO() 
 * @date 2015-9-10 下午04:07:41
 */
public  class constProperty {
	/*redis中用户ID前缀*/
	public final static String USERID_PREFIX = "userFindDevice:";
	
	/*redis中设备ID前缀*/
	public final static String DEVICEGUID_PREFIX = "deviceFindUser:";
	
	/*exist前缀*/
	public final static String EXIST_PREFIX = "Exist:";
	
	/*redis中纯设备信息*/
	public final static String DEVICE = "Device:";
	
	/*设备绑定*/
	public final static String BIND = "0";
	
	/*设备分享*/
	public final static String SHARE = "1";
	
	/*设备类型    1：小维设备*/
	public final static int DEVICE_XIAOWEI = 1;
	
	/*设备类型    0:其他设备    */
	public final static int DEVICE_OTHER = 0;
	
	/*设备报警前缀   */
	public final static String DEVICE_ALARM = "DeviceAlarm:";
	
	//报警开
	public final static String ALARM_ON = "1";
	//报警关
	public final static String ALARM_OFF = "0";
	
	/*redis中用户ID前缀*/
	public final static String Account_PREFIX = "Account:";
	/*redis中验证码前缀*/
	public final static String CHECKCODE_PREFIX = "CheckCode:";
	
	public final static String PHONE_ACCOUNT = "0";
	
	public final static String EMAIL_ACCOUNT = "1";
	
	public final static String WEBURL_PREFIX = "weburl:all";
	
	/*通过type反差weburl对象*/
	public final static String WEBURL_TYPE_PREFIX = "weburl:type";
	
	/*简体中文*/
	public final static String LANGUAGE_CH = "ch";
	/*繁体中文*/
	public final static String LANGUAGE_TW = "tw";
	/*英文*/
	public final static String LANGUAGE_EN = "en";
}
