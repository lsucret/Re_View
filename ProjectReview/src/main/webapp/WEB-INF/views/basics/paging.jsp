<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="col-12 text-center">
	<div id="pagination">
		<c:if test="${paging.prev}">
			<a href="${paging.makeQuery(paging.startPage - 1)}"><i class="fas fa-angle-left"></i></a>
		</c:if>
		<c:forEach begin="${paging.startPage}" end="${paging.endPage}" var="i">
			<a <c:if test="${pagingDefault.page != i}">href="${paging.makeQuery(i)}"</c:if> <c:if test="${pagingDefault.page == i}">class="on"</c:if>>${i}</a>
		</c:forEach>
		<c:if test="${paging.next}">
			<a href="${paging.makeQuery(paging.endPage + 1)}"><i class="fas fa-angle-right"></i></a>
		</c:if>
	</div>
</div>