package com.swag.system.access.service;


import com.swag.common.util.DataBox;
import com.swag.common.util.ValidationUtil;
import com.swag.common.util.pagination.PaginationInfo;
import com.swag.system.access.mapper.AccessMapper;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.ui.ModelMap;

import javax.inject.Inject;

import java.util.List;




@Service
public class AccessService {

	static Logger logger = LoggerFactory.getLogger(AccessService.class);

	@Inject
	private AccessMapper accessMapper;

	public List<DataBox> getIpList(boolean isEndIpNull, String use_yn) {
		String isEndAccessIpNull = "NOT_NULL";
		if (isEndIpNull)
			isEndAccessIpNull = "NULL";

		DataBox selectBox = new DataBox();
		selectBox.put("isEndAccessIpNull", isEndAccessIpNull);
		selectBox.put("use_yn", StringUtils.upperCase(use_yn));

		return accessMapper.selectIpList(selectBox);
	}

	public DataBox getAccessIp(String access_ip, String use_yn) {
		DataBox tempBox = new DataBox();
		tempBox.put("access_ip", access_ip);
		tempBox.put("use_yn", use_yn);

		return accessMapper.selectAccessOne(tempBox);
	}
	
}