package com.mokkoji.app.member;

import java.util.ArrayList;

public class Memberlist {
	
	 ArrayList<Member> memberlist;
	
	public Memberlist(){
		memberlist = new ArrayList<Member>();
	}

	public ArrayList<Member> getMemberlist() {
		return memberlist;
	}

	public void setMemberlist(ArrayList<Member> memberlist) {
		this.memberlist = memberlist;
	}
	
	
}
