package com.mokkoji.app.gschedule;
import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.mokkoji.app.schedule.Schedule;
import com.mokkoji.app.schedule.ScheduleFilter;

@Repository
public class GscheduleDao extends SqlSessionDaoSupport {

	public Integer getGscheduleCount() {
		return getSqlSession().selectOne("gscheduleMapper.getGscheduleCount");
	}
	public Integer selectlastsn() {
		return getSqlSession().selectOne("gscheduleMapper.selectlastsn");
	}


	public Gschedule getGschedule(Integer sn) {
		return getSqlSession().selectOne("gscheduleMapper.getGschedule", sn);
	}
	
	public List<Gschedule> getGscheduleFromgn(Integer gn) {
		return getSqlSession().selectList("gscheduleMapper.getGscheduleFromgn", gn);
	}

	public List<Gschedule> getGschedules(ScheduleFilter gscheduleFilter) {
		return getSqlSession().selectList("gscheduleMapper.getGschedules", gscheduleFilter);
	}
	public void insertGschedule(Gschedule gschedule) {
		getSqlSession().insert("gscheduleMapper.insertGschedule", gschedule);
	}

	public void updateGschedule(Gschedule gschedule) {
		getSqlSession().update("gscheduleMapper.updateGschedule", gschedule);
	}

	public void deleteGschedule(Integer sn) {
		getSqlSession().delete("gscheduleMapper.deleteGschedule", sn);
	}
	
}