package com.swag.common.interceptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.swag.common.util.DataBox;


public class UrlInterceptor implements HandlerInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(UrlInterceptor.class);

	@Value("#{config['MODE']}")
	private String MODE;
	@Value("#{config['MODE.DEVLOGINID']}")
	private String DEV_LOGIN_ID;



	static private List<String> excetionUrl;

	public UrlInterceptor() {
		excetionUrl = new ArrayList<String>();
		excetionUrl.add("/");
		excetionUrl.add("/n0App/menu");
		excetionUrl.add("/n0App/menu/getList");
	
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String requestURI = request.getRequestURI();
		HttpSession session = request.getSession();
		return true;
		/*

		// 예외 URL 처리
		if (excetionUrl.contains(requestURI)) {
			logger.debug("Permition All URL");
			return true;
		}

		return this.sendRedirectMain(request, response);*/
	}

	// 메인페이지 이동을 위한 공통 메소드
	private boolean sendRedirectMain(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.sendRedirect(request.getContextPath().concat("/"));
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse res, Object handler, ModelAndView mv) {
		HttpSession session = req.getSession();
		DataBox userMap = (DataBox) session.getAttribute("user");
		try{
			if (req.getHeader("ajax") != null){
				mv.addObject("currentMenuFunctionList", userMap.getString("CURRENT_MENU_FUNCTION_LIST"));
				return;
			}
			if(!mv.getViewName().equals("main/login")){
				mv.addObject("currentMenuFunctionList", userMap.getString("CURRENT_MENU_FUNCTION_LIST"));
			}
		}catch(Exception e){
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest req, HttpServletResponse res, Object handler, Exception ex) {
	}
}
