package com.swag.common.util.sms.mapper;

import java.util.List;

import javax.annotation.Resource;
import javax.inject.Named;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.swag.common.util.DataBox;

@Repository
public class SmsMapper {
	@Resource
	@Named("sqlSessionAppTo")
	private SqlSession sqlSession;
	
	
	
	public int insertSms(DataBox box) {
		return sqlSession.insert("com.swag.common.util.sms.mapper.SmsMapper.insertSms", box);
	}
	
	public int insertNoCostSms(DataBox box) {
		return sqlSession.insert("com.swag.common.util.sms.mapper.SmsMapper.insertNoCostSms", box);
	}
	
}
