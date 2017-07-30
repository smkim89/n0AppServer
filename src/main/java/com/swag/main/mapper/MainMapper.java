package com.swag.main.mapper;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.swag.common.util.DataBox;

import javax.annotation.Resource;
import javax.inject.Named;

import java.util.List;
import java.util.Map;

/**
 * Created by user on 2015-07-31.
 */
@Repository
public class MainMapper {
	@Resource
	@Named("sqlSessionAppTo")
	private SqlSession sqlSession;

	public List<Map<String, Integer>> selectPayLiveAmtByMin() {
		return sqlSession.selectList("com.nzero.main.mapper.MainMapper.selectPayLiveAmtByMin");
	}
	
	public List<DataBox> selectNoticeList() {
		return sqlSession.selectList("com.nzero.main.mapper.MainMapper.selectNoticeList");
	}
}
