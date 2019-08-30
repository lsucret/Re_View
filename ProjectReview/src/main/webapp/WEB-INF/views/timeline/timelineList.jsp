<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:include page="../basics/header.jsp" />

<script>
	window.onload = function() {
		viewTimeLine(1);
	};

	function viewTimeLine(page) {
		$.ajax({
			url : "/timeline/list",
			type : "POST",
			dataType : "json",
			data : {
				"page" : page
			},
			success : function(result) {
				var list = result.timelineList;
				if (list.length == 0) {
					if ($("#cnt_follow").text() == 0) { // 팔로수가 0 일때
						$(".timelineList").html("<div class='text-center'>이웃을 팔로하여 새 글을 받아보세요!</div>");
					} else {
						$(".timelineList").html("<div class='text-center'>이웃 새 글이 없습니다.</div>");
					}
					return;
				}

				var viewHTML = "", viewBtn = "";
				for (var i = 0; i < list.length; i++) {
					viewHTML = '<div class="blog-entry mb-4" style="overflow: visible;">';
					if (list[i].alert == 1) {
						viewHTML += '<img src="/img/alert_new.png" class="alert-new" style="top: 43px;left: -12px;">';
					}
					viewHTML += '<div class="d-flex align-items-center pb-2"><div class="one mr-2">';
					viewHTML += '<span class="img list-profile rounded-circle" style="background-image: url(' + list[i].member_profile + '); width: 42px; height: 42px;"></span>';
					viewHTML += '</div><div class="two"><a href="/blog/' + list[i].num_url + '" title="블로그 바로가기"><b>' + list[i].member_nickname + '</b></a>';
					viewHTML += '<font class="d-block" style="font-size: .8em;">' + list[i].review_insertDate + '</font></div></div>';
					viewHTML += '<a href="javascript:goReviewDetailFn(\'' + list[i].num_url + '\',\'' + list[i].review_num + '\')" class="block-20 rounded"';
					viewHTML += ' style="background-image: url(\'' + list[i].title_img + '\');" title="리뷰 상세보기"></a>';
					viewHTML += '<div class="text mt-3 d-block"><div class="post_text_1">';
					viewHTML += '<p class="heading" onclick="goReviewDetailFn(\'' + list[i].num_url + '\',\'' + list[i].review_num + '\')" title="리뷰 상세보기">';
					viewHTML += '<font style="color: '+list[i].title_color+';"><img src="/img/star'+list[i].review_grade+'.png" class="review_icon">' + list[i].review_title + '</font></p>';
					viewHTML += '<i class="fas fa-map-marker-alt"></i>' + list[i].store_name;
					viewHTML += '<span class="d-block content-preview">' + removeTag(list[i].review_content) + '</span><div class="post_icon"><ul class="mb-0">';
					viewHTML += '<li><i class="far fa-comment-alt"></i>' + list[i].reply_count + ' Comment</li>';
					viewHTML += '<li><i class="far fa-heart"></i>' + list[i].heart_count + ' Like</li>';
					viewHTML += '<li><i class="far fa-eye"></i>' + list[i].hits + ' Hits</li></ul>';
					viewHTML += '</div></div></div></div>';
					$(".timelineList").append(viewHTML);
				}

				if (result.nextData != null) {
					viewBtn = "<button class='col-12 mt-3 btn-more rounded' onclick='viewTimeLine(" + (Number(page) + 1) + ")'>";
					viewBtn += "더 보기 <i class='fas fa-angle-down'></i></button>";
				}
				$(".viewNext").html(viewBtn);
			}
		});
	}
</script>

<section class="ftco-section">
	<div class="container rounded">
		<div class="row">
			<div class="col-lg-9 ">
				<div class="timelineList"></div>
				<div class="viewNext"></div>
			</div>

			<!-- 사이드 --><jsp:include page="../basics/navblog.jsp" /></div>
	</div>
</section>

<jsp:include page="../basics/footer.jsp" />