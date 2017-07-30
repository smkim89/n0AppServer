package com.swag.n0.menu.mapper;

import java.util.List;

import javax.annotation.Resource;
import javax.inject.Named;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.swag.common.util.DataBox;

@Repository
public class MenuMapper {

	@Resource
	@Named("sqlSessionAppTo")
	private SqlSession sqlSession;
	
	public List<DataBox> getList(DataBox box) {		
		return sqlSession.selectList("com.swag.n0.menu.mapper.MenuMapper.getList", box);
	}
	
	public List<DataBox> getOrderList(int orderId) {		
		return sqlSession.selectList("com.swag.n0.menu.mapper.MenuMapper.getOrderList", orderId);
	}
	
	public int insertOrder(DataBox box) {
		return sqlSession.insert("com.swag.n0.menu.mapper.MenuMapper.insertOrder", box);
	}
	
	public int insertOrderList(DataBox box) {
		return sqlSession.insert("com.swag.n0.menu.mapper.MenuMapper.insertOrderList", box);
	}
	
	public DataBox getMenu(int menuSq) {
		return sqlSession.selectOne("com.swag.n0.menu.mapper.MenuMapper.getMenu", menuSq);
	}
}
