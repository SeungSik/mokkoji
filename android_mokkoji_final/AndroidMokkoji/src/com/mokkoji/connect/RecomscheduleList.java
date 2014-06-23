package com.mokkoji.connect;

import java.util.ArrayList;

public class RecomscheduleList {
	
	private ArrayList<Recomschedule> schedulelist;
	
	public RecomscheduleList(){
		schedulelist = new ArrayList<Recomschedule>();
	}

	public ArrayList<Recomschedule> getSchedulelist() {
		return schedulelist;
	}

	public void setScheduleList(ArrayList<Recomschedule> schedulelist) {
		this.schedulelist = schedulelist;
	}
	
	
}
