<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ include file="../layout/header.jspf"%>
<a href="${cp }/board/modify?pn=${board.pn}">수정</a>
<a href="${cp }/board/delete?pn=${board.pn}">삭제</a>
<ul>
	<li>
		제목 : <c:out value="${board.title }"/> | 글쓴이 : <c:out value="${board.userPn }"/>
	</li>
	<li>
		등록날짜 : <c:out value="${board.regDate }"/> | 조회수 : <c:out value="${board.hitCount }"/>
	</li>
	<li>
		내용 : <c:out value="${board.content }"/>
	</li>
</ul>
<%@ include file="../layout/footer.jspf"%>
