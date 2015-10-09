/**
 * 
 */
package com.jovision.common;

/**
 * @Title: ErrorCodeDef.java 
 * @Package com.jovision.common
 * @author Hellon(刘海龙)
 * @Description: TODO(错误码定义) 
 * @date 2015-9-14 上午11:05:55
 */
public final class ErrorCodeDef {
	
	//操作成功
	public final static String DO_SUCCESS = "000";
	
	/***1 开头代表数据库或其他基础操作出现的错误   ***********************************/
	//mysql数据库连接或操作失败
	public final static String MYSQL_ERROR = "101";
	//参数传入不正确
	public final static String PARAM_ERROR = "102";
	//字符转换数字出错
	public final static String STRING2INT_ERROR = "103";
	//系统内部错误
	public final static String SYSTEM_EXCEPTION = "104";
	//redis异常
	public final static String REDIS_EXCEPTION = "105";
	//远程连接异常
	public final static String REMOTE_EXCEPTION = "106";
	//返回结果为空
	public final static String RESULT_IS_NULL = "107";
	
	
	
	
	
	/***2开头代表设备管理出现的错误 ***********************************/
	//设备不存在
	public final static String DEVICE_NOT_EXIST = "201";
	//用户没有绑定该设备
	public final static String DEVICE_USER_NOTHAVE = "202";
	//设备已被被人绑定
	public final static String DEVICE_BIND_BY_OTHERS = "203";
	//设备已被自己绑定
	public final static String DEVICE_BIND_BY_SELF = "204";
	//设备不在线
	public final static String DEVICE_IS_NOT_ACTIVE = "205";
	//设备验证失败
	public final static String DEVICE_VERIFY_FAIL = "206";
	
	
	/***3 开头代表报警出现的错误 ***********************************/
	//报警开关参数如初错误
	public final static String ALARM_SWITCH = "301";
	
	
	
	/***4 开头代表账号、验证码出现的错误 ***********************************/
	//账号已存在
	public final static String ACCOUNT_EXIT = "401";
	//验证码不合法
	public final static String VALID_CODE_FAIL = "402";
	//用户未登录或登陆超时
	public final static String LOGIN_ERROR = "403";
	//账号不存在
	public final static String ACCOUNT_NO_EXIT = "404";
	//老密码错误
	public final static String OLD_PASSWORD_FAIL = "405";
	//新密码错误
	public final static String NEW_PASSWORD_FAIL = "406";
	//参数为空
	public final static String PARAMETER_IS_NULL = "407";
	//参数不合法
	public final static String PARAMETER_INVALID = "408";
	
	/****5开头代表其他出现的错误******************************************************/
	//版本没有更新
	public final static String OTHER_VERSION_NOTUPDATED = "501";
	//版本过高
	public final static String OTHER_VERSION_ERROR = "502";
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
