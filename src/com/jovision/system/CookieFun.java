package com.jovision.system;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class CookieFun {

	/**
	 * 设置Cookie
	 * 
	 * @param response
	 * @param key
	 * @param value
	 * @param time
	 * @param path
	 */
	public static void setCookie(HttpServletResponse response, String key,
			String value, int time, String path) {

		Cookie cookie = new Cookie(key, value);

		cookie.setMaxAge(time);// 保存3天；

		if (isNull(path)) {
			cookie.setPath("/");
		} else {
			cookie.setPath(path);
		}

		response.addCookie(cookie);
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return
	 */
	private static Boolean isNull(String str) {

		if (str == null) {

			return true;
		} else if (str.equals("null") || str.equals("")) {

			return true;
		} else {

			return false;
		}
	}
}
