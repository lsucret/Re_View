<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="map_wrap">
	<div id="map" class="rounded"></div>

	<!-- 지도 확대, 축소 컨트롤 -->
	<div class="custom_zoomcontrol rounded-top" style="top: 0px; right: 0px;">
		<span onclick="zoomIn()"><img src="http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/ico_plus.png" title="확대"></span> <span onclick="zoomOut()"><img
			src="http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/ico_minus.png" title="축소"></span>
	</div>

	<!-- 현 위치 컨트롤 div -->
	<div class="custom_curLocation rounded-bottom" style="top: 73px; right: 0px;">
		<span onclick="geoFindMe()"><img src="/img/current_location.png" title="현재위치"></span>
	</div>

	<div class="menu-option">
		<form onsubmit="searchPlaces(); return false;">
			<input type="text" id="searchStore" class="b-1 rounded mb-1" placeholder="Search" autofocus>
			<button type="submit" class="btn btn-block" style="margin: 0">Search</button>
		</form>
	</div>
</div>

<link rel="stylesheet" href="/css/kakao.css">
<script>
	$(function() {
		$('#searchStore').keydown(function(e) {
			if (e.keyCode == 13) {
				event.preventDefault();
				searchPlaces();
			}
		});
	});

	function searchPlaces() { // 키워드 검색을 요청하는 함수입니다
		var keyword = $("#searchStore").val();

		if (!keyword.replace(/^\s+|\s+$/g, '')) {
			alert('키워드를 입력해 주세요.');
		} else {
			let keywordArr = keyword.split(" ");
			while (keywordArr.indexOf("") !== -1) {
				keywordArr.splice(keywordArr.indexOf(""), 1);
			}
			form = {
				keyword : keywordArr
			};

			$.ajax({
				type : "post",
				url : "/main/map/search",
				data : JSON.stringify(form),
				contentType : "application/json",
				success : function(data) {
					console.log(data);
					setFilteredMarker(data);
				}
			});
		}
	}

	function setFilteredMarker(data) {
		var points = [];
		var bounds = new kakao.maps.LatLngBounds();
		markers.length = 0; // 배열 초기화

		for (var i = 0; i < data.length; i++) {
			var src = "/img/marker" + Math.round(data[i].avg_grade) + ".png", size = new kakao.maps.Size(20, 26), markerImage = new kakao.maps.MarkerImage(src, size);
			markers.push(new kakao.maps.Marker({
				position : new kakao.maps.LatLng(data[i].store_lat, data[i].store_lng),
				image : markerImage,
				zIndex : 1
			}));
		}

		clusterer.clear();
		clusterer.addMarkers(markers); // 클러스터러에 마커들을 추가합니다
		clusterer.redraw();

		for ( var i in markers) {
			setMarkerListener(markers[i]);
		}

		for (i = 0; i < markers.length; i++) { // LatLngBounds 객체에 좌표를 추가합니다
			bounds.extend(markers[i].getPosition());
		}
		map.setBounds(bounds);
	}

	var lat = "37.40204074", lon = "127.10819300", markers = [] /*마커를 담을 배열*/, currentMap = {};
	geoFindMe();

	var map = new kakao.maps.Map(document.getElementById('map'), { // 지도를 표시할 div
		center : new kakao.maps.LatLng(lat, lon), // 지도의 중심좌표 
		level : 5
	// 지도의 확대 레벨
	});

	var clusterer = new kakao.maps.MarkerClusterer({ // 마커 클러스터러를 생성합니다 
		map : map, // 마커들을 클러스터로 관리하고 표시할 지도 객체 
		averageCenter : true, // 클러스터에 포함된 마커들의 평균 위치를 클러스터 마커 위치로 설정 
		minLevel : 10
	});

	$.get("/main/map/list?lat=" + lat + "&lng=" + lon, function(data, status) { // 현재 위치 받아서 전체 marker 출력
		for (var i = 0; i < data.length; i++) {
			var src = "/img/marker" + Math.round(data[i].avg_grade) + ".png", size = new kakao.maps.Size(20, 26), markerImage = new kakao.maps.MarkerImage(src, size);
			markers.push(new kakao.maps.Marker({
				position : new kakao.maps.LatLng(data[i].store_lat, data[i].store_lng),
				image : markerImage,
				zIndex : 1
			}));
		}

		clusterer.addMarkers(markers); // 클러스터러에 마커들을 추가합니다
		for ( var i in markers) {
			setMarkerListener(markers[i]);
		}
	});

	function displayMarker(locPosition, message) { //지도에 마커와 인포윈도우를 표시하는 함수입니다
		var marker = new kakao.maps.Marker({ // 마커를 생성합니다
			map : map,
			position : locPosition,
			zIndex : 1
		});
		map.setCenter(locPosition); // 지도 중심좌표를 접속위치로 변경합니다
	}

	function setMarkerListener(marker) {
		kakao.maps.event.addListener(marker, 'click', function() { // 마커를 클릭했을 때 커스텀 오버레이를 표시합니다
			$.post("/main/map/info", {
				lat : marker.getPosition().getLat().toFixed(8),
				lng : marker.getPosition().getLng().toFixed(8)
			}, function(reviewJObj) {
				setMarkerContent(reviewJObj, marker);
			});
		});
	}

	function setMarkerContent(reviewInfo, marker) {
		var customOverlay = new kakao.maps.CustomOverlay({
			clickable : true,
			position : marker.getPosition(),
			zIndex : marker.getZIndex() + 2
		});

		var content = '<div class="wrap" style="bottom:26px;"><div class="info">';
		content += '<div class="title"><i class="fas fa-map-marker-alt" style="font-size: .85em !important"></i> ' + reviewInfo[0].store_name;
		content += '<div class="close" id="closeBtn" title="닫기"></div></div><div class="body" style="overflow: auto;">';
		content += '<table class="w-100" style="font-size:.9em;">';
		reviewInfo.forEach(function(item, i) {
			content += '<tr>';
			content += '<td class="pl-1 pnt" style="width: 60%;" onclick=goReviewDetailFn("' + reviewInfo[i].num_url + '",' + reviewInfo[i].review_num + ')>' + reviewInfo[i].review_title + '</td>';
			content += '<td class="text-center"><i class="fas fa-star"></i>' + reviewInfo[i].review_grade + '</td>';
			content += '<td class="text-center">';
			content += '<span class="img list-profile rounded-circle mr-1" style="background-image: url(' + reviewInfo[i].member_profile + ');"></span> ' + reviewInfo[i].member_nickname + '</td>';
			content += '</tr>';
		});
		content += '</table></div></div></div>';

		if (currentMap.customOverlay) { // 이전 오버레이 닫기
			currentMap.marker.setZIndex(1);
			currentMap.customOverlay.setMap(null);
		}

		/* 오버레이 열기 */
		marker.setZIndex(marker.getZIndex() + 1); // 현재 마커가 다른 마커에게 가려지지 않습니다.
		customOverlay.setContent(content); // 오버레이 내용을 세팅합니다
		customOverlay.setMap(map); // 오버레이창을 엽니다.

		currentMap.marker = marker;
		currentMap.customOverlay = customOverlay;
		kakao.maps.event.preventMap(); // 커스텀오버레이에서 마우스 관련 이벤트가 발생해도 지도가 움직이지 않도록 합니다

		$("#map").on("mouseover", ".wrap", function() {
			map.setZoomable(false);
		});
		$("#map").on("mouseout", ".wrap", function() {
			map.setZoomable(true);
		});

		$("#closeBtn").click(function() { // X표시로 오버레이 닫기
			currentMap.customOverlay.setMap(null);
		});

		kakao.maps.event.addListener(map, 'click', function() { // 맵 클릭으로 오버레이 닫기
			currentMap.customOverlay.setMap(null);
		});
	}

	function zoomIn() {
		map.setLevel(map.getLevel() - 1);
	}

	function zoomOut() {
		map.setLevel(map.getLevel() + 1);
	}

	function geoFindMe() { // ip주소로 현위치 검색. Https 환경에서만 작동.
		function success(position) {
			var centerLat = position.coords.latitude;
			var centerLng = position.coords.longitude;
			var moveLatLon = new kakao.maps.LatLng(centerLat, centerLng); // 현위치를 기준으로 지도 재설정
			map.setCenter(moveLatLon);
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
</script>