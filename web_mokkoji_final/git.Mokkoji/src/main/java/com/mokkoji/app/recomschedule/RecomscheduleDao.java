package com.mokkoji.app.recomschedule;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.mokkoji.app.schedule.Schedule;


@Repository
public class RecomscheduleDao extends SqlSessionDaoSupport {
	
	
	public Recomschedule getRecomschedule(Integer userPn) {
		return getSqlSession().selectOne("recomscheduleMapper.getRecomschedule", userPn);
	}
	
	public void insertRecomschedule(Recomschedule recomschedule) {
		getSqlSession().insert("recomscheduleMapper.insertRecomschedule", recomschedule);
	}
	
	public void deleteRecomschedule(Integer rn) {
		getSqlSession().delete("recomscheduleMapper.deleteRecomschedule", rn);
	}
	public List<Recomschedule> getRecomSchedules(Integer userPn) {
		return getSqlSession().selectList("recomscheduleMapper.getRecomSchedules",
				userPn);
	}//filter를 pn으로 바꿨음

}
