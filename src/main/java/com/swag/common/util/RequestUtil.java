package com.swag.common.util;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

public class RequestUtil {

	public static DataBox getStringDataBoxConvert(HttpServletRequest request){
		DataBox map = new DataBox();
		
		Enumeration<String> names = request.getParameterNames();
		while(names.hasMoreElements()){
			String name = (String) names.nextElement();
			map.put(name, request.getAttribute(name));
		}
		return map;
	}
	
}
