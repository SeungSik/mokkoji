package com.mokkoji.app.group;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class GroupDao extends SqlSessionDaoSupport {

	public List<Group> getGroupList() {
		return getSqlSession().selectList("groupMapper.getGroupList");
	}

	public Group getGroupFromgn(Integer gn) {
		return getSqlSession().selectOne("groupMapper.getGroupFromgn", gn);
	}
	
	public Group getGroupFromgname(String gname) {
		return getSqlSession().selectOne("groupMapper.getGroupFromgname", gname);
	}
	public Group getGroupFrommasterPn(Integer masterPn) {
		return getSqlSession().selectOne("groupMapper.getGroupFrommasterPn", masterPn);
	}

	public void insertGroup(Group group) {
		getSqlSession().insert("groupMapper.insertGroup", group);
	}

	public void updateGroup(Group group) {
		getSqlSession().update("groupMapper.updateGroup", group);
	}

	public void deleteGroup(Integer pn) {
		getSqlSession().delete("groupMapper.deleteGroup", pn);
	}

}