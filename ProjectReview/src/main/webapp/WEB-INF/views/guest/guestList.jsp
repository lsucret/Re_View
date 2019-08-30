<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:include page="../basics/header.jsp" />

<section class="ftco-section">
	<div class="container rounded">
		<div class="row">
			<div class="col-lg-9">
				<!-- 검색 -->
				<form action="/blog/guest/${memberDTO.num_url}" id="searchForm" class="search-group" method="get">
					<div class="form-group d-block w-100">
						<div class="d-flex">
							<input type="text" class="form-control" id="searchTxtField" name="searchTxtField" placeholder="Search" value="${pagingDefault.searchTxtField}">
							<button type="submit" class="btn-search" title="검색">
								<i class="fas fa-search"></i>
							</button>
						</div>
					</div>
				</form>

				<textarea name="message" id="guest_content" class="form-control rounded" <c:if test="${empty login}">placeholder="로그인 후 이용가능합니다." style="border: 1px solid #F2F2F2" readonly</c:if>
					<c:if test="${not empty login}">placeholder="방명록 내용 입력" autofocus</c:if>></textarea>
				<div class="d-block w-100 mt-2 pb-5">
					<input type="checkbox" value="1" id="insert_hidden" <c:if test="${empty login}">disabled</c:if>> <label for="insert_hidden" style="cursor: pointer;">비밀글</label> <input
						type="button" class="btn btn_guestWrite" style="float: right;" value="방명록 등록" <c:if test="${empty login}">disabled</c:if>>
				</div>

				<h3 class="mb-3 insertCntVal">
					<span id="cCnt">${paging.totalCount}</span>&ensp;Comments
				</h3>

				<div class="comment_area clearfix">
					<ul class="comment-list">
						<c:forEach var="guest" items="${guestList}">
							<li class="comment">
								<div class="comment-content d-flex">
									<c:choose>
										<c:when test="${guest.hidden_check == 1 && (guest.writer_num != login.member_num && MenuVisible == false)}">
											<i class="fas fa-lock vcard rounded-circle"></i>
											<div class="comment-body">
												<p class='text_hidden'>비밀글 입니다</p>
											</div>
										</c:when>
										<c:otherwise>
											<div class="vcard img rounded-circle" style="background-image: url(${guest.member_profile});"></div>
											<div class="comment-body">
												<h3>
													<c:if test="${guest.hidden_check==1}">
														<i class="fas fa-lock"></i>
													</c:if>
													<c:choose>
														<c:when test="${empty login || login.num_url != guest.num_url}">
															<c:set var="viewURL" value="href='/blog/${guest.num_url}'" />
															<c:set var="viewTitle" value="title='${guest.member_nickname}님 블로그 바로가기'" />
														</c:when>
														<c:otherwise>
															<c:set var="viewURL" value="" />
															<c:set var="viewTitle" value="" />
														</c:otherwise>
													</c:choose>
													<a ${viewURL} ${viewTitle}>${guest.member_nickname}</a><i
														<c:if test="${login.member_num == guest.writer_num}">
																class="far fa-edit viewModalContent" data-id="${guest.guest_num}" data-toggle="modal" data-target="#ModalGuestContentModify" title="방명록 수정"
															</c:if>></i><i
														<c:if test="${login.member_num == guest.blogger_num || login.member_num == guest.writer_num}">
																class="far fa-trash-alt" onclick="guestDataDelete('guest','${guest.guest_num}');" title="방명록 삭제"
															</c:if>></i>
												</h3>

												<div class='meta'>${guest.guest_insertDate}</div>
												<p class='text'>${guest.guest_content}</p>

												<c:if test="${not empty login || guest.reply_count > 0}">
													<!-- 방명록 댓글 리스트 --><%@include file="guestReply.jsp"%>
												</c:if>
											</div>
										</c:otherwise>
									</c:choose>
								</div>
							</li>
						</c:forEach>
					</ul>
				</div>

				<!-- 페이징 --><jsp:include page="../basics/paging.jsp" />
			</div>

			<!-- 사이드 --><jsp:include page="../basics/navblog.jsp" />
		</div>
	</div>
</section>

<script src="/js/project/guest.js"></script>
<jsp:include page="../modal/guestEdit.jsp" />

<jsp:include page="../basics/footer.jsp" />