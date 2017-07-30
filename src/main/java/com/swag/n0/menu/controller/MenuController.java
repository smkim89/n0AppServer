package com.swag.n0.menu.controller;

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
@RequestMapping(value="/n0App/menu")
public class MenuController {
	
	static Logger logger = LoggerFactory.getLogger(MenuController.class);
	
	@Inject
	private MenuService menuService;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String list(
			@RequestParam(value = "page", defaultValue = "1") int pageNo,
			HttpServletRequest request, ModelMap model) {
		DataBox dataBox = RequestUtil.getStringDataBoxConvert(request);
		if(dataBox.getString("userId").isEmpty()){
			return "user/JoinUser";
		}
		model.addAttribute("page", dataBox.getString("page"));
		model.addAttribute("groupId", dataBox.getString("groupId"));
		model.addAttribute("uuid", dataBox.getString("uuid"));
		model.addAttribute("userId", dataBox.getString("userId"));
		return "menu/MenuList";
	}
	
	
	@RequestMapping(value = "/getList", method = RequestMethod.POST)
	public String getGoodsList(
			@RequestParam(value = "page", defaultValue = "1") int pageNo,
			HttpServletRequest request, ModelMap model) {
		DataBox dataBox = RequestUtil.getStringDataBoxConvert(request);
		PaginationInfo paginationInfo = new PaginationInfo(pageNo);
		List<DataBox> goodsList = new ArrayList<DataBox>();
		try {
			if(!dataBox.getString("groupId").isEmpty()){
					goodsList = menuService.selectGoodsList(dataBox, paginationInfo);
				if(goodsList.size()>0){
					model.addAttribute("result", true);
					model.addAttribute("list", goodsList);
					model.addAttribute("page", pageNo);
				}else{
					model.addAttribute("result", false);
					model.addAttribute("list", goodsList);
					model.addAttribute("page", pageNo);
				}	
			}else{
				//TODO 요청정보가 부족합니다. 
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return "menu/MenuListView";
	}
	
	@RequestMapping(value = "/order/insert", method = RequestMethod.POST)
	@ResponseBody
	public String getUserClass(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		DataBox dataBox = RequestUtil.getStringDataBoxConvert(request);
		logger.info(dataBox.toString());
		try {
			int orderAmt = menuService.getAmt(dataBox.getInt("menuSq"), dataBox.getInt("menuCnt"));
			int orderSq = 0;
			dataBox.put("orderAmt", orderAmt);
			
			//orderKey 생성
			if(dataBox.getString("orderSq").isEmpty()){
				orderSq = menuService.insertOrder(dataBox);
				dataBox.put("orderSq", orderSq);
			}else{
				orderSq = dataBox.getInt("orderSq");
			}
			
			//orderList 생성
			int listCnt = menuService.insertOrderList(dataBox);
		
			if(orderSq>0 && listCnt>0){
				result.accumulate("result", true);
				result.accumulate("orderSq", orderSq);
			}else{
				result.accumulate("result", false);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result.toString();
	}
	
	
	@RequestMapping(value = "/order/{orderId}", method = RequestMethod.GET)
	public String orderList(HttpServletRequest request, @PathVariable int orderId, ModelMap model) {
		List<DataBox> orderList = new ArrayList<DataBox>();
		try{
			orderList = menuService.selectOrderList(orderId);
			model.addAttribute("orderList", orderList);
		}catch(Exception e){
			
		}
		return "menu/OrderList";
	}
	
}

