<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="../basics/header.jsp" />

<section class="ftco-section">
	<div class="container rounded">
		<div class="row">
			<div class="col-12">
				<div class="review-title-zone rounded p-4 bg-light">
					<h2 class="font-weight-bold" id="kakao-share-title">
						<img src="/img/star${review.review_grade}.png" width="36px"><font style="margin-left: 7px; color: ${review.title_color};">${review.review_title}</font>
					</h2>
					<span class="d-block"><c:forEach var="i" begin="1" end="5">
							<c:choose>
								<c:when test="${i <= review.review_grade}">
									<i class="fas fa-star"></i>
								</c:when>
								<c:otherwise>
									<i class="far fa-star"></i>
								</c:otherwise>
							</c:choose>
						</c:forEach></span><font class="font-weight-normal" style="color: #888; font-size: .9em;" id="kakao-share-category">${review.category_name}&ensp;/&ensp;조회수 ${review.hits}</font>

					<c:if test="${empty login || login.member_num != review.member_num}">
						<div class="card writer-info">
							<div class="card-header mt-2" id="headingInfo">
								<span class="collapsed pnt" data-toggle="collapse" data-target="#collapseWriterInfo" aria-expanded="false" aria-controls="collapseThree" title="작성자 정보 보기"><i
									class="far fa-address-card"></i>&ensp;작성자</span>
							</div>
							<div id="collapseWriterInfo" class="collapse" aria-labelledby="headingInfo" data-parent="#accordionExample">
								<div class="card-body p-0 mt-2">
									<div class="info-profile">
										<div class="align-items-center">
											<div class="img logo rounded-circle" style="background-image: url(${review.member_profile});"></div>
											<div class="desc">
												<a href="/blog/${memberDTO.num_url}" class="font-weight-bold" title="블로그 바로가기">${review.member_nickname}</a>
												<c:if test="${not empty login}">
													<span class="insertFollowState"><i class="fas fa-envelope ml-2" onclick="showMessageModal('${review.member_num}', '${review.member_nickname}','')" title="메세지 보내기"></i> <script>
														followStateView("${memberDTO.num_url}");
													</script></span>
												</c:if>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</c:if>

					<c:if test="${login.member_num == review.member_num}">
						<div class="btn-review-modify">
							<i class="fas fa-ellipsis-v" title="게시글 관리"></i>
							<ul class="review-cog-box">
								<li class="review-cog-box-li" onclick="location.href='/blog/review/modify/${review.review_num}';"><i class="fas fa-edit"></i>수정</li>
								<li class="review-cog-box-li" onclick="deleteReview();"><i class="fas fa-trash"></i>삭제</li>
							</ul>
						</div>
					</c:if>
				</div>

				<!-- 리뷰 장소 -->
				<div style="border-bottom: 1px solid #F2F2F2;" class="pb-3 text-center">
					<span class="text-right d-block" style="font-size: .8em;">등록일 ${review.review_insertDate}<br>최종수정일 ${review.review_updateDate}
					</span>
					<div id="map" style="width: 380px; height: 250px; display: inline-block;"></div>
				</div>

				<!-- 게시글 내용 -->
				<div class="review-content mt-5">${review.review_content}</div>

				<!-- 게시글 좋아요 및 공유 -->
				<span class="like-share-box mt-3" style="font-size: 1.2em;"><i id="reviewUpdateLike" title="리뷰 좋아요"></i> <font id="reviewLikeCnt"></font><img
					src="//dev.kakao.com/assets/img/about/logos/kakaolink/kakaolink_btn_small.png" id="kakao-link-btn" title="카카오톡 공유" style="float: right; width: 28px;"><input type="hidden"
					id="kakao-share-url"></span>

				<!-- 	댓글 작성부분	 -->
				<blockquote class="blockquote mt-3">
					<form name="frm_write" id="frm_write" method="post" accept-charset="utf-8">
						<textarea name="comment_content" id="comment_content" class="form-control rounded"
							<c:if test="${empty login}">placeholder="로그인 후 이용가능합니다." style="border: 1px solid #F2F2F2" readonly</c:if> <c:if test="${not empty login}">placeholder="댓글 내용 입력"</c:if>></textarea>
						<div class="text-right">
							<button type="button" class="btn mt-3" <c:if test="${empty login}">disabled</c:if>
								<c:if test="${not empty login}">onclick="writeReply('${review.review_num}', '${login.member_num}', '${login.num_url}')"</c:if>>댓글 등록</button>
						</div>
					</form>

					<!-- 댓글 리스트 부분 -->
					<h3 class="insertCntVal mb-3">
						<span id="cCnt"></span>&ensp;Comments
					</h3>
					<div class="comment_area clearfix bdt">
						<ul id="replyHtml" class="comment-list"></ul>
						<div class="view-btn-more"></div>
					</div>
				</blockquote>
			</div>
		</div>
	</div>
</section>

<link rel="stylesheet" href="/css/kakao.css">
<script src="/js/project/review.detail.js"></script>
<script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>
<script>
	selectReviewReplyList('${review.review_num}', '${login.member_num}', '${memberDTO.num_url}', '1');

	$.post("/blog/review/detail/like", { // 좋아요 수 출력
		review_num : '${review.review_num}'
	}, function(result) {
		$("#reviewLikeCnt").text(result.total);
		if (result.state < 1) { // 로그아웃 or 좋아요 안한상태
			$("#reviewUpdateLike").attr("class", "far fa-heart");
		} else { // 좋아요상태
			$("#reviewUpdateLike").attr("class", "fas fa-heart");
		}

		$("#reviewUpdateLike").attr("onclick", "reviewLike('${review.review_num}')");
		if ('${login.num_url}' == '') {
			$("#reviewUpdateLike").removeAttr("onclick");
			$("#reviewUpdateLike").attr("onclick", "if (confirm('로그인이 필요합니다. 로그인 하시겠습니까?')) {location.href = '/account/login';}");
		}
	}, 'json');

	var mapContainer = document.getElementById('map'), // 지도의 중심좌표
	mapOption = {
		center : new kakao.maps.LatLng(Number('${review.store_lat}') + 0.0005, '${review.store_lng}'), // 지도의 중심좌표
		level : 3
	};

	var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
	map.setDraggable(false); // 마우스 드래그로 지도 이동 가능여부를 설정합니다
	map.setZoomable(false); // 마우스 휠로 지도 확대,축소 가능여부를 설정합니다

	var imageSrc = '/img/marker${review.review_grade}.png', // 마커이미지의 주소입니다
	imageSize = new kakao.maps.Size(20, 26); // 마커이미지의 크기입니다
	var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize), markerPosition = new kakao.maps.LatLng('${review.store_lat}', '${review.store_lng}'); // 마커가 표시될 위치입니다

	var marker = new kakao.maps.Marker({ //지도에 마커를 표시합니다 
		map : map,
		position : new kakao.maps.LatLng('${review.store_lat}', '${review.store_lng}'),
		image : markerImage
	});

	var content = '<div class="wrap" style="bottom:26px;"><div class="info">'
	content += '<div class="title"><i class="fas fa-map-marker-alt" style="font-size: .85em !important"></i> ${review.store_name}</div>'
	content += '<div class="body"><div class="img" style="background-image: url(${review.title_img});"></div><div class="desc"><div class="ellipsis">${review.store_addr}</div>'
	content += '<div class="jibun ellipsis"><a href="https://map.kakao.com/link/map/${review.store_name},${review.store_lat},${review.store_lng}" style="color:#FF7E7E" target="_blank">큰지도보기</a></div></div></div></div></div>';

	var overlay = new kakao.maps.CustomOverlay({ //마커 위에 커스텀오버레이를 표시합니다
		content : content,
		map : map,
		position : marker.getPosition()
	});

	kakao.maps.event.addListener(marker, 'click', function() { //마커를 클릭했을 때 커스텀 오버레이를 표시합니다
		overlay.setMap(map);
	});
</script>

<!-- 댓글 수정 --><jsp:include page="../modal/reviewReply.jsp" />
<!-- 쪽지보내기 --><jsp:include page="../modal/msgList.jsp" />
<jsp:include page="../basics/footer.jsp" />