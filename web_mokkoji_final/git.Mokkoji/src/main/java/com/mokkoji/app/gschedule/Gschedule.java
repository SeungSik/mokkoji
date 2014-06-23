package com.mokkoji.app.gschedule;

public class Gschedule {
	
	private Integer sn;
	private Integer gn;
	private Integer user_pn;
	private String title;
	private String content;
	private String startDate;
	private String endDate;
	private Integer importance;

	
	public Integer getSn() {
		return sn;
	}


	public void setSn(Integer sn) {
		this.sn = sn;
	}


	public Integer getGn() {
		return gn;
	}


	public void setGn(Integer gn) {
		this.gn = gn;
	}


	public Integer getUser_pn() {
		return user_pn;
	}


	public void setUser_pn(Integer user_pn) {
		this.user_pn = user_pn;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getStartDate() {
		return startDate;
	}


	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}


	public String getEndDate() {
		return endDate;
	}


	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


	public Integer getImportance() {
		return importance;
	}


	public void setImportance(Integer importance) {
		this.importance = importance;
	}



	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	


	@Override
	public String toString() {
		return "GSchedule [sn=" + sn + ", gn=" + gn
				+ " user_pn= " + user_pn + ", title=" + title + ", content="
				+ content + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", importance=" + importance.toString() + "]";
	}

}