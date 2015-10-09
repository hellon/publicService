package com.jovision.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.jovision.system.ConfigBean;
import com.jovision.thread.ZMQServerThread;



@SuppressWarnings("serial")
public class ZeroMQServerServlet extends HttpServlet {
	
	public void init() throws ServletException {
		ConfigBean cb = ConfigBean.getInstace();
		//1.在servlet中启动server线程
		Thread thread = new ZMQServerThread(cb.getZeroServicePort());
		thread.start();
	}

}
