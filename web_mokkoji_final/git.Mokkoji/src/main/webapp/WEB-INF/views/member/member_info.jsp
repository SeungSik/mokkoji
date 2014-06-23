<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../layout/header.jspf"%>
<table>
	<tr>
		<td>아이디</td>
		<td><c:out value="${member.id }" /></td>
	</tr>
	<tr>
		<td>비밀번호</td>
		<td><c:out value="${member.password }" /></td>
	</tr>
	<tr>
		<td>이메일</td>
		<td><c:out value="${member.email }" /></td>
	</tr>
	<tr>
		<td colspan=2><a href="${cp }/memberList">List</a></td>
	</tr>
</table>
<%@ include file="../layout/footer.jspf"%>