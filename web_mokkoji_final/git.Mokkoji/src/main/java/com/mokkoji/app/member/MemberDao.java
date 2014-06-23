package com.mokkoji.app.member;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDao extends SqlSessionDaoSupport {

	public List<Member> getMemeberList() {
		
		return getSqlSession().selectList("memberMapper.getMemberList");
	}

	public Member getMemberFromId(String id) {
		return getSqlSession().selectOne("memberMapper.getMemberFromId", id);
	}
	
	public Member getMemberFromPn(Integer pn) {
		return getSqlSession().selectOne("memberMapper.getMemberFromPn", pn);
	}

	public void insertMember(Member member) {
		getSqlSession().insert("memberMapper.insertMember", member);
	}

	public void updateMember(Member member) {
		getSqlSession().update("memberMapper.updateMember", member);
	}

	public void deleteMember(Integer pn) {
		getSqlSession().delete("memberMapper.deleteMember", pn);
	}

	
}
