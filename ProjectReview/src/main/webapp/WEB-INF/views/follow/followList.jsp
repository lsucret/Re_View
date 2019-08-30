<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:include page="../basics/header.jsp" />

<script>
	var page = "${nowPageData}";

	$(document).ready(function() {
		if (page == 'following') {
			$("#page_follower").removeClass("active");
			$("#page_following").addClass("active");
		} else if (page == 'followers') {
			$("#page_following").removeClass("active");
			$("#page_follower").addClass("active");
		}

		$.ajax({
			url : "/blog/follow/" + page + "/list", // follow, follower List 받을 URL
			type : "POST",
			dataType : "json",
			success : function(result) {
				printBlogFollowList(page, result);
			}
		});
	});
</script>

<section class="ftco-section">
	<div class="container rounded">
		<div class="row">
			<div class="col-lg-9">
				<ul class="nav nav-tabs">
					<li class="nav-item"><a class="nav-link active" id="page_following" href="#" onclick="location.replace('/blog/follow/following')">Following</a></li>
					<li class="nav-item"><a class="nav-link" id="page_follower" href="#" onclick="location.replace('/blog/follow/followers')">Followers</a></li>
				</ul>

				<!-- 리스트 출력란 -->
				<div class="tab-content"></div>
			</div>

			<!-- 사이드 --><jsp:include page="../basics/navblog.jsp" />
		</div>
	</div>
</section>

<script src="/js/project/follow.js"></script>
<jsp:include page="../basics/footer.jsp" />