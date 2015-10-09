package com.jovision.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.jovision.system.ConfigBean;



@SuppressWarnings("serial")
public class ConfigBeanInitServlet extends HttpServlet {

	public void init() throws ServletException {
		// Put your code here
		//使用配置文件配置动态文件
		ConfigBean cb = ConfigBean.getInstace();
		this.getServletContext().setAttribute("configBean", cb);
		
	}

}
