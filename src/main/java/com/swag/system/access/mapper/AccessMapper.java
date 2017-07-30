package com.swag.system.access.mapper;



import java.util.List;

import javax.annotation.Resource;
import javax.inject.Named;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.swag.common.util.DataBox;

@Repository
public class AccessMapper {
	@Resource
	@Named("sqlSessionAppTo")
	private SqlSession sqlSession;
	
	public int insertAccess(DataBox box) {
		return sqlSession.insert("com.swag.commonManagement.systemManagement.accessManagement.mapper.AccessManagementMapper.insertAccess", box);		
	}
	public List<DataBox> selectAccessList(DataBox box) {
		return sqlSession.selectList("com.swag.commonManagement.systemManagement.accessManagement.mapper.AccessManagementMapper.selectAccessList", box);
	}
	
	public List<DataBox> selectIpList(DataBox box) {
		return sqlSession.selectList("com.swag.commonManagement.systemManagement.accessManagement.mapper.AccessManagementMapper.selectIpList", box);
	}
	
	public int selectAccessListCount(DataBox box) {
		return sqlSession.selectOne("com.swag.commonManagement.systemManagement.accessManagement.mapper.AccessManagementMapper.selectAccessListCount", box);
	}
	
	public DataBox selectAccessOne(DataBox box) {
		return sqlSession.selectOne("com.swag.commonManagement.systemManagement.accessManagement.mapper.AccessManagementMapper.selectAccessOne", box);
	}
	
	public int updateAccess(DataBox box) {
		return sqlSession.update("com.swag.commonManagement.systemManagement.accessManagement.mapper.AccessManagementMapper.updateAccess", box);		
	}
	
	public int deleteAccess(int access_seq) {
		return sqlSession.delete("com.swag.commonManagement.systemManagement.accessManagement.mapper.AccessManagementMapper.deleteAccess", access_seq);
	}
	
	public int updateUserIp(int access_seq) {
		return sqlSession.update("com.swag.commonManagement.systemManagement.accessManagement.mapper.AccessManagementMapper.updateUserIp", access_seq);		
	}
	
	
}