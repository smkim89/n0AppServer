package com.swag.main.service;

import com.swag.common.util.DataBox;
import com.swag.common.util.pagination.PaginationInfo;
import com.swag.main.mapper.MainMapper;

import org.json.JSONArray;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import javax.inject.Inject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 2015-07-31.
 */
@Service
public class MainService {

	@Inject
	private MainMapper mainMapper;
	

	public String getPayLiveData() {
		List<Map<String, Integer>> list = mainMapper.selectPayLiveAmtByMin();
		JSONArray mainArray = new JSONArray();
		for (Map data : list) {
			Date d = null;
			JSONArray subArray = new JSONArray();
			subArray.put((String) data.get("DT"));
			subArray.put(data.get("AMT"));
			mainArray.put(subArray);
		}
		return mainArray.toString();
	}
	
	
	public List<DataBox> getNoticeList(){
		return mainMapper.selectNoticeList();
	}
}
