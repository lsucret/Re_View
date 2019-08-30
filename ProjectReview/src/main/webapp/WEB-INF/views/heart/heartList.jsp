<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:include page="../basics/header.jsp" />
<script>
	window.onload = function() {
		$("head").append("<script src='/js/project/review.js'><\/script>");
		$('#sort').val("${pagingDefault.sort}");

		var urlData = "bloggerNum=${memberDTO.num_url}&sort=${pagingDefault.sort}&searchTxtField=${pagingDefault.searchTxtField}&category=${pagingDefault.category}";
		$.get("/blog/review/heart/list/read?" + urlData, function(list, status) {
			var viewHTML = "";
			if (list.length == 0) {
				$(".reviewLikeList").html('<span class="text-center w-100">데이터가 존재하지 않습니다.</span>');
				return;
			}

			for (var i = 0; i < list.length; i++) {
				viewHTML = '<div class="col-sm-6 col-md-4">';
				viewHTML += '<div class="blog-entry mb-3 heart-entry"><div onclick="findCategoryVal(\'' + list[i].category_name + '\')" class="p-1 topc">';
				viewHTML += '<i class="fas fa-search" style="font-size: 12px;"></i> ' + list[i].category_name + '</div>';
				viewHTML += '<a href="javascript:goReviewDetailFn(\'' + list[i].num_url + '\',\'' + list[i].review_num + '\')" class="block-20 rounded" ';
				viewHTML += 'style="background-image: url(' + list[i].title_img + ');" title="리뷰 상세보기"></a><div class="text mt-3 d-block">';
				viewHTML += '<div class="d-flex align-items-center p-2 topp" style="font-size: .9em">';
				viewHTML += '<span class="img list-profile rounded-circle mr-1" style="background-image: url(' + list[i].member_profile + ');"></span>';
				viewHTML += '<a href="/blog/'+list[i].num_url+'" title="블로그 바로가기">' + list[i].member_nickname + '</a></div><div class="post_text_1">';
				viewHTML += '<p class="heading" onclick="goReviewDetailFn(\'' + list[i].num_url + '\',\'' + list[i].review_num + '\')" title="리뷰 상세보기">';
				viewHTML += '<font style="color: '+list[i].title_color+';"><img src="/img/star'+list[i].review_grade+'.png" class="review_icon">' + list[i].review_title + '</font></p>';
				viewHTML += '<i class="fas fa-map-marker-alt"></i>' + list[i].store_name + '<div class="post_icon"><ul>';
				viewHTML += '<li>' + list[i].heart_date + '</li>';
				viewHTML += '<li><i class="fas fa-heart" id="reviewUpdateLike" onclick="reviewLike(\'' + list[i].review_num + '\');" title="좋아요 취소"></i>' + list[i].like_count + ' Like</li></ul>';
				viewHTML += '</div></div></div></div></div>';

				$(".reviewLikeList").append(viewHTML);
			}
		});
	}
</script>

<section class="ftco-section">
	<div class="container rounded">
		<div class="row">
			<div class="col-lg-9">
				<!-- 검색 -->
				<form action="/blog/review/heart/list" id="searchForm" class="search-group d-block w-100" method="get">
					<div class="form-group">
						<select id="sort" name="sort" class="form-column rounded" onchange="this.form.submit()">
							<option value="insert">등록일자순</option>
							<option value="nickname">작성자순</option>
							<option value="store">리뷰장소순</option>
							<option value="grade">평점순</option>
							<option value="like">좋아요순</option>
						</select>
						<div class="d-flex">
							<input type="text" class="form-control" id="searchTxtField" name="searchTxtField" placeholder="Search" value="${pagingDefault.searchTxtField}"> <input hidden="hidden"
								id="category" name="category" value="${pagingDefault.category}">
							<button type="submit" class="btn-search" title="검색">
								<i class="fas fa-search"></i>
							</button>
						</div>
					</div>
				</form>

				<!-- 내용출력 -->
				<div class="row d-flex reviewLikeList"></div>

				<!-- 페이징 --><jsp:include page="../basics/paging.jsp" />
			</div>

			<!-- 사이드 --><jsp:include page="../basics/navblog.jsp" />
		</div>
	</div>
</section>

<jsp:include page="../basics/footer.jsp" />