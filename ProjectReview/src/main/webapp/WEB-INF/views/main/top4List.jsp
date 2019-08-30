<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<div class="col-12 pb-3 mt-5 heading-section text-center">
	<span class="subheading">last week's</span>
	<h2 class="main-wrap-bottom">best review</h2>
</div>

<c:choose>
	<c:when test="${fn:length(top4ReviewList) > 0}">
		<c:forEach items="${top4ReviewList}" var="top4">
			<div class="col-md-6">
				<c:if test="${top4.review_grade > 3}">
					<img src="/img/alert_recommend.png" class="alert-new">
				</c:if>
				<div class="blog-entry mb-3 main-blog d-flex">
					<div class="d-flex align-items-center p-2 topp" style="font-size: .9em">
						<span class="img list-profile rounded-circle mr-1" style="background-image: url(${top4.member_profile});"></span><a href="/blog/${top4.num_url}" title="블로그 바로가기">${top4.member_nickname}</a>
					</div>
					<a href="javascript:goReviewDetailFn('${top4.num_url}','${top4.review_num}')" class="block-20 rounded" style="width: 40%; height: 200px; background-image: url('${top4.title_img}');"
						title="리뷰 상세보기"></a>
					<div class="text d-block" style="width: 60%; margin-left: 10px;">
						<div class="post_text_1">
							<p class="heading" onclick="goReviewDetailFn('${top4.num_url}','${top4.review_num}')" title="리뷰 상세보기">
								<font style="color: ${top4.title_color};"><img src="/img/star${top4.review_grade}.png" class="review_icon">${top4.review_title}</font>
							</p>
							<i class="fas fa-map-marker-alt"></i>${top4.store_name}
							<p class="writer_data">${top4.category_name}&ensp;/&ensp;${top4.review_insertDate}</p>
							<div class="post_icon">
								<ul>
									<li><i class="far fa-comment-alt"></i>${top4.reply_count} Comment</li>
									<li><i class="far fa-heart"></i>+${top4.reviewlike_week} Like</li>
									<li><i class="far fa-eye"></i>${top4.hits} Hits</li>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
		</c:forEach>
	</c:when>
	<c:otherwise>
		<div class="sidebar-box">
			<span class="d-block text-center rounded pt-5 pb-5" style="border: 1px dashed #CFD0C4;">지난주 베스트 리뷰가 없습니다.</span>
		</div>
	</c:otherwise>
</c:choose>
