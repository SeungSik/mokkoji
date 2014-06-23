package com.mokkoji.app.board;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class BoardDao extends SqlSessionDaoSupport {

	public Integer getBoardCount(BoardFilter boardFilter){
		return getSqlSession().selectOne("boardMapper.getBoardCount");
	}
	
	public Board getBoard(Integer pn) {
		return getSqlSession().selectOne("boardMapper.getBoard", pn);
	}

	public List<Board> getBoards(BoardFilter boardFilter) {
		return getSqlSession().selectList("boardMapper.getBoards", boardFilter);
	}

	public void insertBoard(Board board) {
		getSqlSession().insert("boardMapper.insertBoard", board);
	}

	public void updateBoard(Board board) {
		getSqlSession().update("boardMapper.updateBoard", board);
	}

	public void deleteBoard(Integer pn) {
		getSqlSession().delete("boardMapper.deleteBoard", pn);
	}

}
