package com.mokkoji.app.schedule;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;


@Repository
public class ScheduleDao extends SqlSessionDaoSupport {

	public Integer getScheduleCount(ScheduleFilter scheduleFilter) {
		return getSqlSession().selectOne("scheduleMapper.getScheduleCount");
	}

	public Schedule getSchedule(Integer sn) {
		return getSqlSession().selectOne("scheduleMapper.getSchedule", sn);
	}

	public List<Schedule> getSchedules(Integer userPn) {
		return getSqlSession().selectList("scheduleMapper.getSchedules",
				userPn);
	}//filter를 pn으로 바꿨음

	public void insertSchedule(Schedule schedule) {
		getSqlSession().insert("scheduleMapper.insertSchedule", schedule);
	}

	public void updateSchedule(Schedule schedule) {
		getSqlSession().update("scheduleMapper.updateSchedule", schedule);
	}

	public void deleteSchedule(Integer sn) {
		getSqlSession().delete("scheduleMapper.deleteSchedule", sn);
	}
}