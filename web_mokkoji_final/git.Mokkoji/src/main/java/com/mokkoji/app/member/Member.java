package com.mokkoji.app.member;

public class Member {

	private Integer pn;
	private String id;
	private String password;
	private String email;

	public Member() {
	}

	public Member(Integer pn, String id, String password, String email) {
		super();
		this.pn = pn;
		this.id = id;
		this.password = password;
		this.email = email;
	}

	public Integer getPn() {
		return pn;
	}

	public void setPn(Integer pn) {
		this.pn = pn;
	}

	public String getEmail() {
		return email;
	}

	public String getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Member [pn=" + pn + ", id=" + id + ", password=" + password
				+ ", email=" + email + "]";
	}

}
