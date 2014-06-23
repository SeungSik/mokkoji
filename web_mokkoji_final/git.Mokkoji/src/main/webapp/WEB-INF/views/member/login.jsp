<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ include file="../layout/header.jspf"%>
<c:choose>
	<c:when test="${sMember ne null }">
		Welcome <c:out value="${sMember.id}"/>! <a href="${cp }/logout">Logout</a>
	</c:when>
	<c:otherwise>
		<form:form commandName="member" action="${cp }/login" method="post">
			<table>
				<tr>
					<td>
						<b><font size=5>로그인 페이지</font></b>
					</td>
				</tr>
				<tr>
					<td>아이디<form:input path="id"/></td>
				</tr>
				<tr>
					<td>비밀번호<form:password path="password"/></td>
				</tr>
				<tr>
					<td colspan="2">
						<button type="submit">로그인</button>
						<a href="${cp }/join">회원가입</a>
					</td>
				</tr>
			</table>
			</form:form>	
	</c:otherwise>
</c:choose>

<%@ include file="../layout/footer.jspf"%>