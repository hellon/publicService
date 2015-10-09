package com.jovision.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.jovision.aes.AESCrypt;
import com.jovision.aes.BizAccSession;
import com.jovision.system.ConfigBean;
import com.jovision.utils.Tools;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
/**
 * 设置所有action 的基类，用于处理一些统一的操作
 * @author 
 */
@SuppressWarnings("serial")
public class BaseController extends ActionSupport {
	protected static Logger logger = Logger.getLogger(BaseController.class);
	protected static Logger infoLogger = Logger.getLogger("forInfo");
	/**
	 * 获得 ServletActionContext.getRequest()
	 * @return HttpServletRequest
	 */
	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	/**
	 * 获得 ServletActionContext.getResponse();
	 * @return HttpServletResponse
	 */
	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}
	
	/**
	 * 获得 ServletActionContext.getRequest().getSession();
	 * @return HttpSession
	 */
	public HttpSession getSession() {
		return ServletActionContext.getRequest().getSession();
	}
	
	/**
	 * 获得ActionContext.getContext().getSession();
	 * @return
	 */
	public Map<String, Object> mapSession() {
		return ActionContext.getContext().getSession();
	}
	
	/**
	 * 获得application;
	 * @return
	 */
	public ServletContext getApplication() {
		return ServletActionContext.getServletContext();
	}
	

	/**
	 * 获取系统配置文件对象
	 * @return
	 */
	protected ConfigBean getConfigBean(){
		return (ConfigBean)getApplication().getAttribute("configBean");
	}

	/**
	 * 通过解密工具获取用户信息
	 * @author Hellon(刘海龙) 
	 * @return 为空则没登陆或登陆超时
	 */
	protected BizAccSession getBizAccSession(){
		String sid = getRequest().getParameter("sid");
		if(Tools.strIsNull(sid)){
			return null;
		}
		
		ConfigBean config = getConfigBean();
		
		byte[] decryptFrom = AESCrypt.parseHexStr2Byte(sid);  
		
		try {
			BizAccSession ab = AESCrypt.decrypt_session(decryptFrom, config.getAesPasseord());
			
			if(ab == null){
				return null;
			}
			ab.setUsername(ab.getUsername().trim());
			String loginTime = new Date(ab.getTimestamp()).toLocaleString();
			String nowTime = new Date(System.currentTimeMillis()).toLocaleString();
			
			//当前时间
			long currentTime = System.currentTimeMillis();
			//登陆后一小时的时间
			long allowTime = ab.getTimestamp()+3600*1000;
			if(allowTime>=currentTime){//登陆没有超时
				StackTraceElement stack[] = (new Throwable()).getStackTrace();
				for (int i = 0; i < stack.length; i++)
				{
					StackTraceElement s = stack[i];
					if(s.getClassName().endsWith(this.getClass().getSimpleName())){
						infoLogger.info("用户：["+ab.getUsername().trim()+"] 登陆成功, 操作："+this.getClass().getName()+"."+s.getMethodName()+"()方法");
						break;
					}
				}
				return ab;
			}else{
				infoLogger.info("用户：["+ab.getUsername()+"] 登陆时间："+loginTime+",请求时间："+nowTime+",登陆超时！");
				return null;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	} 
	
}
