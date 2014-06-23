<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ include file="../layout/header.jspf"%>

<form:form commandName="member" action="${cp }/join" method="post">
	<table border=1>
		<tr>
			<td>아이디</td>
			<td><form:input path="id"/></td>
		</tr>
		<tr>
			<td>비밀번호</td>
			<td><form:password path="password"/></td>
		</tr>
		<tr>
			<td>이메일</td>
			<td><form:input path="email"/></td>
		</tr>
		<tr>
			<td colspan="2">
				<button type="submit">회원가입</button>
			</td>
		</tr>
	</table>
</form:form>
<%@ include file="../layout/footer.jspf"%>
