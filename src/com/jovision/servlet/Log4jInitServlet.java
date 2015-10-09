package com.jovision.servlet;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.PropertyConfigurator;

public class Log4jInitServlet extends HttpServlet {


	public void init() throws ServletException {
		// Put your code here
		String prefix =  getServletContext().getRealPath("/");
	    String file = getInitParameter("log4j-init-file");
	    // if the log4j-init-file is not set, then no point in trying
	    if(file != null) {
	      PropertyConfigurator.configure(prefix+file);
	    }
	}

}
