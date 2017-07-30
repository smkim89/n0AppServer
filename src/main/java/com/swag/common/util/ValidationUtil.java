package com.swag.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ValidationUtil {
	static Logger logger = LoggerFactory.getLogger(ValidationUtil.class);
	
	
	//데이터의 년월을 받아서 (시작값 종료값) 통계의 종료일을 설정한다.
	public static String returnEndDay(String startDate, String endDate){
		Calendar today = Calendar.getInstance();
		today.add(Calendar.DATE, 0);
		Calendar endDayCal = Calendar.getInstance();
		String endYear = endDate.replaceAll("-", "").substring(0,4);
    	String endMonth = endDate.replaceAll("-", "").substring(4,6);
		endDayCal.set(Calendar.YEAR, Integer.parseInt(endYear));
    	endDayCal.set(Calendar.MONTH, Integer.parseInt(endMonth)-1);
    	endDayCal.set(Calendar.DAY_OF_MONTH, endDayCal.getActualMaximum(Calendar.DAY_OF_MONTH));
    	String todayMonth = String.valueOf((today.get(Calendar.MONTH)+1));
    	if(Integer.parseInt(todayMonth) < 10){
    		todayMonth = "0"+todayMonth;
    	}
    	if(endDate.replaceAll("-", "").equals(String.valueOf(today.get(Calendar.YEAR))+todayMonth)){
	    	endDayCal.set(Calendar.YEAR, Integer.parseInt(endYear));
	    	endDayCal.set(Calendar.MONTH, Integer.parseInt(endMonth)-1);
	    	endDayCal.set(Calendar.DAY_OF_MONTH, today.get(Calendar.DAY_OF_MONTH)-1);
	    }
    	String search_end_day =  "";
	    if((endDayCal.get(Calendar.MONTH)+1) < 10){
	    	search_end_day = String.valueOf(endDayCal.get(Calendar.YEAR))+"0"+String.valueOf((endDayCal.get(Calendar.MONTH)+1))+String.valueOf(endDayCal.get(Calendar.DAY_OF_MONTH));
	    }else{
	    	search_end_day =  String.valueOf(endDayCal.get(Calendar.YEAR))+String.valueOf((endDayCal.get(Calendar.MONTH)+1))+String.valueOf(endDayCal.get(Calendar.DAY_OF_MONTH));
	    }
		
		return search_end_day;
	}
	
}
