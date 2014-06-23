package com.mokkoji.connect;

import java.util.ArrayList;

public class ScheduleList {
	
	private ArrayList<Schedule> schedulelist;
	
	public ScheduleList(){
		schedulelist = new ArrayList<Schedule>();
	}

	public ArrayList<Schedule> getSchedulelist() {
		return schedulelist;
	}

	public void setScheduleList(ArrayList<Schedule> schedulelist) {
		this.schedulelist = schedulelist;
	}
	
	
}
