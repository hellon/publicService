package com.jovision.http.Annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType; 

/** 
 * 作用在方法上，处理该方法是不是需要登陆验证
 * @author liuhailong 
 * @date 2015-07-22
 */  
@Documented//文档  
@Retention(RetentionPolicy.RUNTIME)//在运行时可以获取  
@Target({ ElementType.TYPE, ElementType.METHOD })//作用到类，方法，接口上等 
public @interface PermissionValidate {
	 //枚举类型  
    public enum PermissionTypeEnum {  
        OFF, //登陆不需要验证
        ON   //登陆需要验证
    }  
    //实际的值  
    public PermissionTypeEnum permissionType() default PermissionTypeEnum.OFF; 
}
