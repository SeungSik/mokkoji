package com.mokkoji.app.usergschedule;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;
@Repository
public class UsergscheduleDao extends SqlSessionDaoSupport {

	public List<Usergschedule> getUsergscheduleList() {
		return getSqlSession().selectList("usergscheduleMapper.getUsergschedulelist");
	}
	
	public List<Usergschedule> getUsergscheduleFromgs_sn(Integer gs_sn){
		return getSqlSession().selectList("usergscheduleMapper.getUsergscheduleFromgs_sn", gs_sn);
	}
	public List<Usergschedule> getUsergscheduleFromgs_gn(Integer gs_gn){
		return getSqlSession().selectList("usergscheduleMapper.getUsergscheduleFromgs_gn", gs_gn);
	}
	
	public List<Usergschedule> getUsergscheduleFromuser_pn(Integer user_pn){
		return getSqlSession().selectList("usergscheduleMapper.getUsergscheduleFromuser_pn",user_pn);
	}
	public List<Usergschedule> getUsergscheduleFromug(Usergschedule ug){
		return getSqlSession().selectList("usergscheduleMapper.getUsergscheduleFromug",ug);
	}
		
	public void insertUsergschedule(Usergschedule usergschedule){
		getSqlSession().insert("usergscheduleMapper.insertUsergschedule", usergschedule);
	}
	
	public void updateUsergschedule(Usergschedule usergschedule) {
		getSqlSession().update("usergscheduleMapper.updateGroupUsergschedule", usergschedule);
	}

	public void deleteUsergschedule(Integer gs_sn) {
		getSqlSession().delete("usergscheduleMapper.deleteUsergschedule", gs_sn);
	}
	

}
