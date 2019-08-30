/**
 * 리뷰 장소 검색하는 스크립트
 */

var ps = new kakao.maps.services.Places(); // 장소 검색 객체를 생성합니다

// 키워드 검색을 요청하는 함수입니다
function searchPlaces() {
	var keyword = document.getElementById("searchStore").value;
	if (!keyword.replace(/^\s+|\s+$/g, '')) {
		alert('키워드를 입력해 주세요.');
		return false;
	}
	ps.keywordSearch(keyword, placesSearchCB); // 장소검색 객체를 통해 키워드로 장소검색을 요청합니다
}

// 장소검색이 완료됐을 때 호출되는 콜백함수 입니다
function placesSearchCB(data, status, pagination) {
	if (status === kakao.maps.services.Status.OK) {
		displayPlaces(data); // 정상적으로 검색이 완료됐으면 검색 목록과 마커를 표출합니다
		displayPagination(pagination); // 페이지 번호를 표출합니다
	} else if (status === kakao.maps.services.Status.ZERO_RESULT) {
		alert('검색 결과가 존재하지 않습니다.');
		return;
	} else if (status === kakao.maps.services.Status.ERROR) {
		alert('검색 결과 중 오류가 발생했습니다.');
		return;
	}
}

// 검색 결과 목록과 마커를 표출하는 함수입니다
function displayPlaces(places) {
	var listEl = document.getElementById('placesList'), menuEl = document.getElementById('menu_wrap'), fragment = document.createDocumentFragment(), bounds = new kakao.maps.LatLngBounds(), listStr = '';
	removeAllChildNods(listEl); // 검색 결과 목록에 추가된 항목들을 제거합니다

	for (var i = 0; i < places.length; i++) {
		var varPlace = places[i];
		var itemEl = getListItem(i, varPlace); // 검색 결과 항목 Element를 생성합니다

		var sname = "", scategory = "", saddr = "", locationx = "", locationy = "";
		(function(title, category, addr, roadaddr, x, y) {
			itemEl.onmouseover = function() { // 마커와 검색결과 항목에 mouseover 했을때 해당 장소에 인포윈도우에 장소명을 표시합니다
				sname = title;
				scategory = category;
				if (roadaddr == "") {
					saddr = addr;
				} else {
					saddr = roadaddr;
				}
				locationx = x;
				locationy = y;
			};
		})(places[i].place_name, places[i].category_group_code, places[i].address_name, places[i].road_address_name, places[i].x, places[i].y);
		fragment.appendChild(itemEl);

		$(itemEl).on("click", function() {
			$("#searchStore").val(""); // 검색 내역 삭제
			$("#placesList, #pagination").empty(); // 검색 내역 삭제
			$("#searchStoreModal").modal("hide"); // 모달 닫기

			$("#store_name").val(sname); // 가게명 등록
			var nowSelectedVal = $("#store_category option:selected").val();
			if ((nowSelectedVal == "" || nowSelectedVal != scategory) && scategory != "") {
				$('#store_category').val(scategory);
			}

			$("#store_info").val(saddr + "###" + Number.parseFloat(locationy).toFixed(8) + "###" + Number.parseFloat(locationx).toFixed(8));
		});
	}

	listEl.appendChild(fragment); // 검색결과 항목들을 검색결과 목록 Elemnet에 추가합니다
	menuEl.scrollTop = 0;
}

// 검색결과 항목을 Element로 반환하는 함수입니다
function getListItem(index, places) {
	var el = document.createElement('li'), itemStr = '<p class="markerbg marker_' + (index + 1) + '"></p><span class="store_name font-weight-bold">' + places.place_name + '</span>';
	if (places.road_address_name) {
		itemStr += '<span>' + places.road_address_name + '</span>';
	} else {
		itemStr += '<span>' + places.address_name + '</span>';
	}

	el.innerHTML = itemStr;
	el.className = 'item';
	return el;
}

// 검색결과 목록 하단에 페이지번호를 표시는 함수입니다
function displayPagination(pagination) {
	var paginationEl = document.getElementById('pagination'), fragment = document.createDocumentFragment(), i;

	// 기존에 추가된 페이지번호를 삭제합니다
	while (paginationEl.hasChildNodes()) {
		paginationEl.removeChild(paginationEl.lastChild);
	}

	for (i = 1; i <= pagination.last; i++) {
		var el = document.createElement('a');
		el.href = "#";
		el.innerHTML = i;

		if (i === pagination.current) {
			el.className = 'on';
		} else {
			el.onclick = (function(i) {
				return function() {
					pagination.gotoPage(i);
				}
			})(i);
		}

		fragment.appendChild(el);
	}
	paginationEl.appendChild(fragment);
}

// 검색결과 목록의 자식 Element를 제거하는 함수입니다
function removeAllChildNods(el) {
	while (el.hasChildNodes()) {
		el.removeChild(el.lastChild);
	}
}