<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ include file="../layout/header.jspf"%>
<c:if test="${sMember eq null and sMember.id eq board.userPn }">
<script>
	alert('권한이 존재하지 않습니다.');
	location.href='${cp}/board/list';
</script>
</c:if>
<form:form commandName="board" action="${cp }/board/modify" method="post">
	<input type="hidden" value="${sMember.pn }" name="userPn"/>
	<form:hidden path="pn"/>
	<ul>
		<li>
			제목 : <form:input path="title"/>
		</li>
		<li>
			글쓴이 : <c:out value="${sMember.id }"/>
		</li>
		<li>
			내용 : <form:textarea path="content"/>
		</li>
		<li>
			<button type="submit">전송</button>
		</li>
	</ul>
</form:form>
<%@ include file="../layout/footer.jspf"%>
