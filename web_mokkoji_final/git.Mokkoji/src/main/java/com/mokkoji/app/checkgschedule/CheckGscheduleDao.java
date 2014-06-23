package com.mokkoji.app.checkgschedule;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class CheckGscheduleDao extends SqlSessionDaoSupport {

	public List<CheckGschedule> getCheckGscheduleList() {
		return getSqlSession().selectList("checkGscheduleMapper.getGroupManagementlist");
	}
	
	public List<CheckGschedule> getCheckGscheduleFromgroup_sn(Integer group_sn){
		return getSqlSession().selectOne("checkGscheduleMapper.getCheckGscheduleFromgroup_gn", group_sn);
	}
	public CheckGschedule getCheckScheduleFromCheck(CheckGschedule cks){
		return getSqlSession().selectOne("checkGscheduleMapper.getCheckScheduleFromCheck", cks);
	}
	public void insertCheckGschedule(CheckGschedule checkGschedule){
		getSqlSession().insert("checkGscheduleMapper.insertCheckGschedule", checkGschedule);
	}
	public void updateCheckGschedule(CheckGschedule checkGschedule) {
		getSqlSession().update("checkGscheduleMapper.updateCheckGschedule", checkGschedule);
	}

	public void deleteCheckGschedule(CheckGschedule checkGschedule) {
		getSqlSession().delete("checkGscheduleMapper.deleteCheckGschedule",checkGschedule);
	}
	

}
