<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../basics/header.jsp" />

<section class="ftco-section">
	<div class="container rounded">
		<div class="row">
			<div class="col-lg-9">
				<!-- 검색 -->
				<form action="/blog/map/${memberDTO.num_url}" id="searchForm" class="search-group d-block w-100" method="get">
					<div class="form-group mb-3">
						<div class="d-flex">
							<input type="text" class="form-control" id="searchTxtField" name="searchTxtField" placeholder="Search">
							<button type="button" class="btn-search" title="검색" onclick="storeListFilter()">
								<i class="fas fa-search"></i>
							</button>
						</div>
					</div>
				</form>

				<div class="d-flex w-100 pt-3">
					<div id="map" class="rounded" style="height: 600px;"></div>
					<!-- 지도 확대, 축소 컨트롤 -->
					<div class="custom_zoomcontrol rounded-top" style="top: auto; right: 15px;">
						<span onclick="zoomIn()"><img src="http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/ico_plus.png" title="확대"></span> <span onclick="zoomOut()"><img
							src="http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/ico_minus.png" title="축소"></span>
					</div>
					<!-- 현 위치 컨트롤 div -->
					<div class="custom_curLocation rounded-bottom" style="top: 143px; right: 15px;">
						<span onclick="geoFindMe(kakaoMap.map)"><img src="/img/current_location.png" title="현재위치"></span>
					</div>
				</div>
			</div>

			<!-- 사이드 --><jsp:include page="../basics/navblog.jsp" />
		</div>
	</div>
</section>

<link rel="stylesheet" href="/css/kakao.css">
<script>
	var reviewArr = {
		0 : [],
		1 : [],
		2 : [],
		3 : [],
		4 : [],
		5 : []
	};
	
	var filterReviewArr = {
		0 : [],
		1 : [],
		2 : [],
		3 : [],
		4 : [],
		5 : []	
	};
	
	var kakaoMap = {
		setClusterer 	: setClusterer,
		setMarker		: setMarker,
	};

	function zoomIn() {
		kakaoMap.map.setLevel(kakaoMap.map.getLevel() - 1);
	}
	
	function zoomOut() {
		kakaoMap.map.setLevel(kakaoMap.map.getLevel() + 1);
	}

	function geoFindMe() { // ip주소로 현위치 검색. Https 환경에서만 작동.
		function success(position) {
			var centerLat = position.coords.latitude;
			var centerLng = position.coords.longitude;
			var moveLatLon = new kakao.maps.LatLng(centerLat, centerLng); // 현위치를 기준으로 지도 재설정
			kakaoMap.map.setCenter(moveLatLon);
		}
		function error() {
			alert('위치정보를 받아오지 못하였습니다.');
		}
		if (!navigator.geolocation) {
			alert('Geolocation은 이 브라우저에서 지원하지 않는 기능입니다.');
		} else {
			navigator.geolocation.getCurrentPosition(success, error);
		}
	}

	function setClusterer() {
		var map = new kakao.maps.Map(document.getElementById('map'), {
			center : new kakao.maps.LatLng(37.4020407438695, 127.10819300477226),
			level : 4
		});
		
		var clusterer = new kakao.maps.MarkerClusterer({
			map : map,
			averageCenter : true,
			minLevel : 6
		});
		
		kakaoMap.map = map;
		kakaoMap.clusterer = clusterer;
		
		geoFindMe();
		setMarker(reviewArr); // 클러스터맵에 마커를 표시합니다.
	}
	
	function setMarker(reviewArr) {
		for (i = 0; i < 6; i++) {
			if (reviewArr[i].length !== 0) {
				var src = "/img/marker" + i + ".png", 
					size = new kakao.maps.Size(20, 26), 
					option = {
						alt : "마커 이미지",
						shape : "poly",
						coords : "0,8, 8,0, 12,0, 19,8, 19,13, 10,26, 9,26, 0,13"
				};

				var markerImage = new kakao.maps.MarkerImage(src, size, option);

				// 데이터에서 좌표 값을 가지고 마커를 표시합니다
				// 마커 클러스터러로 관리할 마커 객체는 생성할 때 지도 객체를 설정하지 않습니다
				var markers = $(reviewArr[i]).map(function(idx, reviewInfo) {
					var position = new kakao.maps.LatLng(reviewInfo.store_lat, reviewInfo.store_lng);
					var marker = new kakao.maps.Marker({
						position : position,
						image : markerImage,
						zIndex : 1
					});
					setCustomOverlay(kakaoMap.map, position, marker, reviewInfo); // 커스텀오버레이 등록 함수

					return marker;
				});
				
				kakaoMap.clusterer.addMarkers(markers); // 클러스터러에 마커들을 추가합니다
			}
		}
	}
	
	function setCustomOverlay(map, position, marker, reviewInfo) { // 커스텀 오버레이를 생성합니다
		var customOverlay = new kakao.maps.CustomOverlay({
			clickable : true,
			position : position,
			yAnchor : 1,
			zIndex : 3
		});

		setListener(marker, customOverlay, reviewInfo);
	}
	
	function setListener(marker, customOverlay, reviewInfo) {
		kakao.maps.event.addListener(marker, 'click', function() { // 마커를 클릭했을 때 커스텀 오버레이를 표시합니다
			/* 이전 오버레이 닫기 */
			if (kakaoMap.currentCustomOverlay) {
				kakaoMap.currentMarker.setZIndex(1);
				kakaoMap.currentCustomOverlay.setMap(null);
			}
			
			/* 오버레이 열기 */
			marker.setZIndex(2); // 현재 마커가 다른 마커에게 가려지지 않습니다.
			customOverlay.setContent(setCustomOverlayContent(reviewInfo)); // 오버레이 내용을 세팅합니다
			customOverlay.setMap(kakaoMap.map); // 오버레이창을 엽니다.

			kakaoMap.currentMarker = marker;
			kakaoMap.currentCustomOverlay = customOverlay;
			kakao.maps.event.preventMap();

			/* X표시로 오버레이 닫기 */
			$("#closeBtn").click(function() {
				kakaoMap.currentCustomOverlay.setMap(null);
			});

			/* 맵 클릭으로 오버레이 닫기 */
			kakao.maps.event.addListener(kakaoMap.map, 'click', function() {
				kakaoMap.currentCustomOverlay.setMap(null);
			});
		});
	}
	
	function setCustomOverlayContent(reviewInfo) {
		var reviewTitle = cutByWord(reviewInfo.review_title, 15),
			reviewContent = cutByWord(reviewInfo.review_content, 20),
			goReviewDetailFnName = "goReviewDetailFn('" + reviewInfo.num_url + "','" + reviewInfo.review_num + "')";

		// 커스텀 오버레이에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
		var content = '<div class="wrap" style="bottom:26px;">' + 
		            '    <div class="info">' + 
		            '        <div class="title">' + 
		            '			 <i class="fas fa-map-marker-alt" style="font-size: .85em !important"></i> ' + reviewInfo.store_name +
		            '            	 <div class="close" id="closeBtn" title="닫기"></div>' + 
		            '        </div>' + 
		            '        <div class="body">' + 
		            '            <div class="img pnt" style="background-image: url(' + reviewInfo.title_img + ');"></div>' + 
		            '            <div class="desc">' + 
		            '			 	 <div onclick=' + goReviewDetailFnName + '>' + 
		            '		 	 		 <font class="pnt" style="color: ' + reviewInfo.title_color + ';">' +
		            '			 	 	 	 ' + reviewTitle + 
		            '			 	 	 </font>' +
		            '				 </div>' +
		            '                <div onclick="' + goReviewDetailFnName + '">' +
		            '					 <font class="pnt" style="font-size: 12px;">' + reviewContent + '</font>' + 
		            '				 </div>' + 
		            '				 <div class="post_icon">' + 
						'					 <ul>' +
						'						 <li><i class="far fa-comment-alt"></i>' + reviewInfo.reply_count + '</li>' +
						'						 <li><i class="far fa-heart"></i>' + reviewInfo.heart_count + '</li>' + 
						'						 <li><i class="far fa-eye"></i>' + reviewInfo.hits + '</li>' + 
						'					 </ul>' + 
						'				 </div>' + 
		            '            </div>' + 
		            '        </div>' + 
		            '    </div>' +    
		            '</div>';
		        	// 가게주소, 별점 추가필요
		return content;
	}

	function cutByWord(textNode, maxCharacterNum) {
		var result = "";
		
		if (textNode.length > maxCharacterNum) {
			result = textNode.substring(0, maxCharacterNum) + "...";
		} else {
			result = textNode;
		}
		
		return result;
	}
	
	function getReviewInfo(mapToggleArr) {
		$.each(mapToggleArr, function(i, item) { 
			for (j = 0; j < 6; j++) {
				if (item.review_grade === j) {
					item.review_content = extractTextFromHTML(item.review_content);
					reviewArr[j].push(item);
				}
			}
		});
	}
	
	function extractTextFromHTML(content) {
		var tmpOneArr = content.replace(/&nbsp;/gi, '').split(">");
		var tmpTwoArr = [];
		var result = "";
		
		for (var i in tmpOneArr) {
			if (tmpOneArr[i].length !== 0) {
				tmpTwoArr = tmpOneArr[i].split("<");
				result += tmpTwoArr[0];
			}
			if (tmpTwoArr[1] == "p" && i > 0) {
				result += " ";
			}
		}
		
		return result;
	}
	
	function storeListFilter() {
		var searchKeyword = $("#searchTxtField").val();
		var points = [];
		var bounds = new kakao.maps.LatLngBounds();
		
		for (i=0; i<6; i++) {
			filterReviewArr[i].length = 0; // 배열(0점 ~ 5점까지) 초기화
			
			if (reviewArr[i].length !== 0) { // 별점
				
				reviewArr[i].forEach(function(item, j) {
					let searchTarget = item.store_name + " " + item.store_addr + 
							" " + item.review_title + " " + item.review_content;

					if (searchTarget.indexOf(searchKeyword) !== -1) {
						filterReviewArr[i].push(item);
						points.push(new kakao.maps.LatLng(item.store_lat, item.store_lng));
					}
				});
			}
		}
		
		if (kakaoMap.currentCustomOverlay) {
			kakaoMap.currentCustomOverlay.setMap(null);
		}
		
		if (filterReviewArr[0].length === 0 && filterReviewArr[1].length === 0 
				&& filterReviewArr[2].length === 0 && filterReviewArr[3].length === 0 
				&& filterReviewArr[4].length ===0 && filterReviewArr[5].length === 0) {
			return alert("검색결과가 없습니다."); // 검색결과가 없을 경우 얼럿
		}
		
		kakaoMap.clusterer.clear();
		setMarker(filterReviewArr); // 마커를 지도에 찍습니다.
		kakaoMap.clusterer.redraw();
		
		for (i = 0; i < points.length; i++) {
		    // LatLngBounds 객체에 좌표를 추가합니다
		    bounds.extend(points[i]);
		}
		kakaoMap.map.setBounds(bounds);
	}
	
	$(function() {
		getReviewInfo(${mapToggle});
		setClusterer();
		
		$('#searchTxtField').keydown(function(e) {
		    if(e.keyCode == 13) {
		    	event.preventDefault();
		    	storeListFilter();
		    }
		});
	});
</script>

<jsp:include page="../basics/footer.jsp" />