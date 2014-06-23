package com.mokkoji.app.group;

public class Group {

	private Integer gn;
	private String gname;
	private Integer masterPn;


	public Integer getGn() {
		return gn;
	}


	public void setGn(Integer gn) {
		this.gn = gn;
	}


	public String getGname() {
		return gname;
	}


	public void setGname(String gname) {
		this.gname = gname;
	}


	public Integer getMasterPn() {
		return masterPn;
	}


	public void setMasterPn(Integer masterPn) {
		this.masterPn = masterPn;
	}


	public Group() {
		
	}


	@Override
	public String toString() {
		return "Group [gn=" + gn + ", gname=" + gname + ", masterPn="
				+ masterPn + "]";
	}

}
