package com.swag.n0.menu.service;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.ui.ModelMap;

import com.swag.common.util.DataBox;
import com.swag.common.util.pagination.PaginationInfo;
import com.swag.n0.menu.mapper.MenuMapper;

@Service
public class MenuService{
	private Logger logger = LoggerFactory.getLogger(MenuService.class);

	@Inject
	private MenuMapper menuMapper;

	public List<DataBox> selectGoodsList(DataBox reqBox, PaginationInfo page) throws Exception{
		reqBox.put("start", page.getFirstRecordIndex());
		reqBox.put("end", page.getLastRecordIndex());
		return menuMapper.getList(reqBox);
	}
	
	public int insertOrder(DataBox box){
		menuMapper.insertOrder(box);
		return box.getInt("orderSq");
	}
	
	public int insertOrderList(DataBox box){
		return menuMapper.insertOrderList(box);
	}
//	
	public List<DataBox> selectOrderList(int orderId) throws Exception{
		
		return menuMapper.getOrderList(orderId);
	}
	
	public int getAmt(int menuSq, int cnt) throws Exception{
		
		DataBox menuBox = menuMapper.getMenu(menuSq);
		
		return menuBox.getInt("MENU_AMT")*cnt;
	}
	
}