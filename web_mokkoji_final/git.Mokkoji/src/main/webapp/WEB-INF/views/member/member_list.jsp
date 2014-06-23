<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../layout/header.jspf"%>
<table>
	<tbody>
		<c:forEach items="${memberList}" var="member">
		<tr>
			<td>
				<a href="${cp }/memberView?pn=${member.pn }"><c:out value="${member.id }"/></a>
			</td>
			<td>
				<a href="${cp }/memberDelete?pn=${member.pn }">삭제</a>
			</td>
		</tr>
		</c:forEach>
	</tbody>
</table>
<%@ include file="../layout/footer.jspf"%>