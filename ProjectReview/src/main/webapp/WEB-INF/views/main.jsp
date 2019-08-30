<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:include page="basics/header.jsp" />
<script src="/js/project/main.js"></script>

<section class="ftco-section">
	<div class="container rounded mt-3">
		<!-- 맵 출력 --><%@include file="main/mainMap.jsp"%>

		<div class="row">
			<!-- 지난주 베스트 리뷰 --><%@include file="main/top4List.jsp"%>

			<!-- 지난달 베스트 유저 -->
			<div class="col-5 mt-5 ml-auto mr-auto text-center">
				<span class="heading-section subheading">last month's<br>best reviewer
				</span>
				<c:choose>
					<c:when test="${fn:length(bestUserList) > 0}">
						<div id="carouselExampleIndicators" class="carousel mt-3 slide" data-ride="carousel">
							<ol class="carousel-indicators" style="bottom: -10px;">
								<c:forEach begin="0" end="${fn:length(bestUserList) - 1}" var="i">
									<li data-target="#carouselExampleIndicators" data-slide-to="${i}" <c:if test="${i == 0}">class="active"</c:if>></li>
								</c:forEach>
							</ol>
							<div class="carousel-inner">
								<c:forEach items="${bestUserList}" var="user" varStatus="i">
									<div class="carousel-item <c:if test="${i.count == 1}">active</c:if>">
										<div class="info-profile">
											<div class="info-profile-wrap align-items-center">
												<div class="img logo logo-main rounded-circle" style="background-image: url(${user.member_profile});">
													<c:if test="${i.count < 4}">
														<img src="/img/best_uesr${i.count}.png" style="width: auto !important; top: -8px; left: 0;">
													</c:if>
												</div>
												<div class="desc">
													<h4 class="profile-name">
														<a href="/blog/${user.num_url}" title="블로그 바로가기">${user.member_nickname}</a>
													</h4>
													<span class="date">지난달 리뷰 수 ${user.month_total}</span>
												</div>
											</div>
										</div>
									</div>
								</c:forEach>
							</div>
						</div>
					</c:when>
					<c:otherwise>
						<div class="sidebar-box">
							<span class="d-block rounded pt-5 pb-5">데이터가 없습니다.</span>
						</div>
					</c:otherwise>
				</c:choose>
			</div>

			<!-- 타임라인 -->
			<div class="col-7 mt-5 ml-auto mr-auto text-center">
				<span class="heading-section subheading">follow<br>new review
				</span>
				<div class="sidebar-box">
					<div class="timelineList"></div>
				</div>
			</div>
		</div>
	</div>

	<!-- 유저 검색 -->
	<div class="container rounded mt-3" style="background: #CFD0C4;">
		<div class="col-12 ml-auto mr-auto text-center">
			<div class="search-group">
				<span class="heading-section subheading">search user</span>
				<div class="form-group mt-3">
					<div class="d-flex" style="width: 100%;">
						<input type="text" class="form-control" id="searchUser" name="searchUser" placeholder="ID or Nickname" onkeyup=" if (window.event.keyCode == 13) {searchUser();}">
						<button type="button" class="btn-search" onclick="searchUser();" title="검색">
							<i class="fas fa-search"></i>
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="modal/searchUser.jsp" />
</section>

<jsp:include page="basics/footer.jsp" />