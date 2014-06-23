package com.mokkoji.connect;

public class Schedule {
	
	private Integer sn; // 스케쥴 넘버

	private Integer userPn; // 사용자 id 넘버

	private String userId;

	private String title; // 일정 제목

	private String content; // 일정 내용

	private String startDate; // 일정 시작

	private String endDate; // 일정 끝

	private Integer importance; // 중요도

	

	public Schedule() {
		// TODO Auto-generated constructor stub
	}

	public Schedule(Integer sn, Integer userPn, String userId, String title,
			String content, String startDate, String endDate,
			Integer importance) {
		this.sn = sn;
		this.userPn = userPn;
		this.userId = userId;
		this.title = title;
		this.content = content;
		this.startDate = startDate;
		this.endDate = endDate;
		this.importance = importance;
		
	}

	public String getContent() {
		return content;
	}

	

	public String getEndDate() {
		return endDate;
	}

	public Integer getImportance() {
		return importance;
	}

	public Integer getSn() {
		return sn;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getTitle() {
		return title;
	}

	public String getuserId() {
		return userId;
	}

	public Integer getUserPn() {
		return userPn;
	}

	public void setContent(String content) {
		this.content = content;
	}

	

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public void setImportance(Integer importance) {
		this.importance = importance;
	}

	public void setSn(Integer sn) {
		this.sn = sn;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setuserId(String userId) {
		this.userId = userId;
	}

	public void setUserPn(Integer userPn) {
		this.userPn = userPn;
	}

	@Override
	public String toString() {
		return "Schedule [sn=" + sn + ", userPn=" + userPn.toString()
				+ " userId= " + userId + ", title=" + title + ", content="
				+ content + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", importance=" + importance.toString() + "]";
	}

}
