package com.mokkoji.function;

import com.mokkoji.connect.Member;

public class MemberSelect {
	
	private boolean selceted;
	private Member member;
	
	public boolean isSelceted() {
		return selceted;
	}
	public void setSelceted(boolean selceted) {
		this.selceted = selceted;
	}
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}

	
}
