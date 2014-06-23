package com.mokkoji.app.gsmanagement;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;
@Repository
public class GsmanagementDao extends SqlSessionDaoSupport {

	public List<Gsmanagement> getGsmanagementList() {
		return getSqlSession().selectList("gsmanagementMapper.getGsmanagementlist");
	}
	
	public List<Gsmanagement> getGsmanagementFromgroup_gn(Integer group_gn){
		return getSqlSession().selectList("gsmanagementMapper.getGsmanagementFromgroup_gn", group_gn);
	}
	
	public GsmanagementDao getUsergscheduleFromgroup_sn(Integer group_sn){
		return getSqlSession().selectOne("gsmanagementMapper.getGsmanagementFromgroup_sn",group_sn);
	}
		
	public void insertGsmanagement(Gsmanagement gsmanagement){
		getSqlSession().insert("gsmanagementMapper.insertGsmanagement", gsmanagement);
	}
	
	public void updateGsmanagement(Gsmanagement gsmanagement) {
		getSqlSession().update("gsmanagementMapper.updateGroupGsmanagement", gsmanagement);
	}

	public void deleteGsmanagement(Integer group_sn) {
		getSqlSession().delete("gsmanagementMapper.deleteGsmanagement", group_sn);
	}
	

}
