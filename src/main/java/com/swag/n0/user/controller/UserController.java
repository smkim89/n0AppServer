package com.swag.n0.user.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.swag.common.util.DataBox;
import com.swag.common.util.RequestUtil;
import com.swag.common.util.pagination.PaginationInfo;
import com.swag.n0.menu.service.MenuService;


@Controller
@RequestMapping(value="/n0App/user")
public class UserController {
	
	static Logger logger = LoggerFactory.getLogger(UserController.class);
	
	
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String list(
			@RequestParam(value = "page", defaultValue = "1") int pageNo,
			HttpServletRequest request, ModelMap model) {
		DataBox dataBox = RequestUtil.getStringDataBoxConvert(request);
		
		return "user/login";
	}
	
	
}

