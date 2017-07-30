package com.swag.main.controller;

import com.swag.common.util.DataBox;
import com.swag.main.service.MainService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

@Controller
@RequestMapping
public class MainController {

	@Inject
	private MainService mainService;

	@RequestMapping("/")
	public String main(HttpServletRequest request, ModelMap model) {
		return "main/main";
	}

	@RequestMapping("payLiveData")
	@ResponseBody
	public String payLiveData() {
		return mainService.getPayLiveData();
	}

	@RequestMapping("forbidden")
	public String forbidden() {
		return "exception/403";
	}
	
	@RequestMapping("sessionError")
	public String sessionError() {
		return "exception/sessionError";
	}
	
	@RequestMapping(value="login", method=RequestMethod.GET)
	public String login(HttpSession session, HttpServletResponse response) {
		DataBox userMap = (DataBox)session.getAttribute("user");

		// 2015-09-15 gupark : 로그인이 되어있다면 메인페이지로 보내버리자
		try {
			if (userMap != null && !StringUtils.isEmpty(userMap.getString("USER_ID")))
				response.sendRedirect("/");
		} catch (IOException e) {
		}

		return "main/login";
	}

}
