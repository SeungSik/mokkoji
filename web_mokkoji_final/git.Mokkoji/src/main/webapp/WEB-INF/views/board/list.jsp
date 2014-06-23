<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ include file="../layout/header.jspf"%>
<%-- Page 처리 Script --%>
<c:set var="pagination" value="${boardFilter.pagination }"/>
<script type="text/javascript">
/* <![CDATA[ */
var numPagesPerScreen = <c:out value='${pagination.numPagesPerScreen}'/>;
var page = <c:out value='${pagination.currentPage}'/>;
var numPages = <c:out value='${pagination.numPages}'/>;

function goToNextPages() {
	goToPage(Math.min(numPages, page + numPagesPerScreen));
}

function goToPage(page) {	
	$("input#page").val(page);
	$("form#boardFilter").submit();
}

function goToPreviousPages() {
	goToPage(Math.max(1, page - numPagesPerScreen));
}
/* ]]> */
</script>

<form:form commandName="boardFilter" method="get">
	<form:hidden path="page" value="${pagination.currentPage}"/>
	<a href="${cp }/board/write">입력</a>
	<button type="submit">검색</button>
</form:form>

<table>
	<thead>
		<tr>
			<th>번호.</th>
			<th>제목</th>
			<th>등록날짜</th>
			<th>조회수</th>
		</tr>
	</thead>
	<tfoot>
		<tr>
			<c:set var="isFirst" value="${pagination.currentPage > 1}"/>
			<c:set var="isPrevious" value="${pagination.numPages - pagination.numPagesPerScreen > 1  }"/>
			<c:set var="isLast" value="${pagination.numPages ne pagination.currentPage }"/>
			<c:set var="isNext" value="${pagination.numPagesPerScreen + pagination.currentPage < numPages }"/>
			<td colspan="4">
				<button type="button" onclick="void(goToPage(1))" class="${isFirst ? 'num_list_first_active' : 'num_list_first' } num_list_btn">&lt;&lt;</button><button type="button" onclick="void(goToPreviousPages())" class="${isPrevious ? 'num_list_previous_active' : 'num_list_previous' } num_list_btn">&lt;</button><c:forEach var="i" begin="${pagination.pageBegin}" end="${pagination.pageEnd}"><c:if test="${i == pagination.currentPage}"><button class="num_list_page_now" type="button">${i}</button></c:if><c:if test="${i != pagination.currentPage}"><button class="num_list_page" onclick="void(goToPage(${i}))" type="button">${i}</button></c:if></c:forEach><button type="button" onclick="void(goToNextPages())" class="${isNext ? 'num_list_next_active' : 'num_list_next' } num_list_btn">&gt;</button><button type="button" onclick="void(goToPage(${pagination.numPages}))" class="${isLast ? 'num_list_last_active' : 'num_list_last' } num_list_btn">&gt;&gt;</button>
			</td>
		</tr>
	</tfoot>
	<tbody>
		<c:forEach var="board" items="${boards }">
			<tr>
				<td><c:out value="${board.pn }"/></td>
				<td><a href="${cp }/board/view?pn=${board.pn}"><c:out value="${board.title }"/></a></td>
				<td><c:out value="${board.regDate}"/></td>
				<td><c:out value="${board.hitCount }"/></td>
    		</tr>
		</c:forEach>
	</tbody>
</table>
<%@ include file="../layout/footer.jspf"%>
