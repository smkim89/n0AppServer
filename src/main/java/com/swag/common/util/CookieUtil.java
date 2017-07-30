package com.swag.common.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * Cookie 관련 유틸리티 클래스
 *
 * @update 2015-06-05
 * @author gupark
 *
 */
public class CookieUtil {
	
	/**
	 * Cookie를 입력한다.
	 * 
	 * @param response
	 * @param key
	 * @param value
	 */
	public static void setCookie(HttpServletResponse response, String key, String value) {
		Cookie cookie = new Cookie(key, value);
		cookie.setMaxAge(24 * 60 * 60);
		cookie.setPath("/");
		response.addCookie(cookie);
	}
	
	/**
	 * Cookie 값을 반환한다.
	 * 
	 * @param request
	 * @param key
	 * @return
	 */
	public static String getCookieValue(HttpServletRequest request, String key){
		String cookieValue = "";
		Cookie[] cookies = request.getCookies();
		
		if (cookies == null)
			return cookieValue;
		
		for (Cookie cookie : cookies) {
			if ( key.equals(cookie.getName()) ) 
				cookieValue = cookie.getValue();
		}
		return cookieValue;
	}
	
	/**
	 * Cookie 를 제거한다.
	 * @param response
	 * @param key
	 */
	public static void removeCookie(HttpServletResponse response, String key) {
		Cookie cookie = new Cookie(key, "");
		cookie.setMaxAge(0);
		response.addCookie(cookie);
	}
	
	public static Cookie getCookie(HttpServletRequest request, String key){
		Cookie c = null;
		Cookie[] cookies = request.getCookies();
		
		if (cookies == null)
			return null;
		
		for (Cookie cookie : cookies) {
			if ( key.equals(cookie.getName()) ) 
				c = cookie;
		}
		return c;
	}
	
}
