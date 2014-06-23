package com.mokkoji.app.gschedule;

import java.util.ArrayList;


public class GscheduleList {


	private ArrayList<Gschedule> gschedulelist;
	
	public GscheduleList(){
		gschedulelist = new ArrayList<Gschedule>();
	}

	public ArrayList<Gschedule> getGschedulelist() {
		return gschedulelist;
	}

	public void setGscheduleList(ArrayList<Gschedule> gschedulelist) {
		this.gschedulelist = gschedulelist;
	}
}
