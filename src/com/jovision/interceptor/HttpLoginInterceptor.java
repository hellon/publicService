package com.jovision.interceptor;

import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.StrutsStatics;
import com.jovision.http.Annotation.PermissionValidate;
import com.jovision.http.Annotation.PermissionValidate.PermissionTypeEnum;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class HttpLoginInterceptor implements Interceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void init() {
		// TODO Auto-generated method stub

	}

	public String intercept(ActionInvocation invocation) throws Exception {
		// TODO Auto-generated method stub
		
		//获取actionContext
		ActionContext  actionContext = invocation.getInvocationContext();
		//得到请求对象
		HttpServletRequest request= (HttpServletRequest) actionContext.get(StrutsStatics.HTTP_REQUEST);
		String sid = request.getParameter("sid")+"";
		
		//获取调用的方法
		String methodName = invocation.getProxy().getMethod();
		//获取调用的类
		Class clazz = invocation.getProxy().getAction().getClass();
		Method method = clazz.getMethod(methodName);
		//获取该方法上的自定义注解
		PermissionValidate  permission = method.getAnnotation(PermissionValidate.class);
		//判断注解上的值是不是需要登陆验证
		if(permission != null&&permission.permissionType().equals(PermissionTypeEnum.ON)){
		}
		return invocation.invoke();
		
	}

}
