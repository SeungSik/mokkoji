package com.mokkoji.app.board;

public class Board {

	private Integer pn;

	private Integer userPn;

	private String title;

	private String content;

	private String regDate;

	private Integer hitCount;

	public Board() {
		super();
	}

	public String getContent() {
		return content;
	}

	public Integer getHitCount() {
		return hitCount;
	}

	public Integer getPn() {
		return pn;
	}

	public String getRegDate() {
		return regDate;
	}

	public String getTitle() {
		return title;
	}

	public Integer getUserPn() {
		return userPn;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setHitCount(Integer hitCount) {
		this.hitCount = hitCount;
	}

	public void setPn(Integer pn) {
		this.pn = pn;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setUserPn(Integer userPn) {
		this.userPn = userPn;
	}

	@Override
	public String toString() {
		return "Board [pn=" + pn + ", userPn=" + userPn + ", title=" + title
				+ ", content=" + content + ", regDate=" + regDate
				+ ", hitCount=" + hitCount + "]";
	}

}
