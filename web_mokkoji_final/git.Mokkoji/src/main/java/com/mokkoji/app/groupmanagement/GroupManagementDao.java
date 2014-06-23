package com.mokkoji.app.groupmanagement;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.mokkoji.app.group.Group;


@Repository
public class GroupManagementDao extends SqlSessionDaoSupport  {

	public List<GroupManagement> getGroupManagementList() {
		return getSqlSession().selectList("groupmanagementMapper.getGroupManagementlist");
	}
	
	public GroupManagementDao getGroupManagementFromuser_pn(Integer user_pn){
		return getSqlSession().selectOne("groupmanagementMapper.getGroupManagementFromuser_pn", user_pn);
	}
	
	public List<GroupManagement> getGroupManagementFromgroup_gn(Integer group_gn){
		return getSqlSession().selectList("groupmanagementMapper.getGroupManagementFromgroup_gn", group_gn);
	}
		
	public void insertGroupManagement(GroupManagement groupmanagement){
		getSqlSession().insert("groupmanagementMapper.insertGroupManagement", groupmanagement);
	}
	
	public void updateGroupManagement(GroupManagement groupmanagement) {
		getSqlSession().update("groupmanagementMapper.updateGroupManagement", groupmanagement);
	}

	public void deleteGroupManagement(GroupManagement groupmanagement) {
		getSqlSession().delete("groupmanagementMapper.deleteGroupManagement",groupmanagement);
	}
	
	
}
