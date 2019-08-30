/**
 * 리뷰관리
 */
window.onload = function() {
	$(".btnrating").on('click', (function(e) { // 리뷰 별점
		var selected_value = $(this).attr("data-attr");
		$("#selected_rating").val(selected_value);
		$(".btnrating").html("<i class='far fa-star'></i>");

		for (i = 1; i <= selected_value; ++i) {
			$("#rating-star-" + i).toggleClass('btn-default');
			$("#rating-star-" + i).html("<i class='fas fa-star'></i>");
		}

		for (ix = 1; ix <= $("#selected_rating").val(); ++ix) {
			$("#rating-star-" + ix).toggleClass('btn-default');
		}
	}));
}

function resetReviewGrade() { // 별점 초기화
	for (i = 1; i <= $("#selected_rating").val(); ++i) {
		$("#rating-star-" + i).toggleClass('btn-default');
	}
	$(".btnrating").html("<i class='far fa-star'></i>");
	$("#selected_rating").val("0");
}

function reviewLike(num) { // 좋아요 추가 및 취소
	var classVal = $("#reviewUpdateLike").attr("class"), addState = true, delCheck = false;
	if (classVal == "fas fa-heart") { // 좋아요 취소 시, 알림문구
		if (confirm("좋아요를 취소 하시겠습니까?")) {
			addState = false; // 좋아요 취소. delete
			delCheck = true;
		}
	}

	if (classVal == "far fa-heart" || delCheck) {
		$.post("/blog/review/heart/update", {
			reviewNum : num,
			addState : addState,
			pageURL : location.href
		}, function(result) {
			if (result.likeCnt == null) {
				location.reload();
			} else {
				$('#reviewUpdateLike').removeClass(classVal);
				$('#reviewUpdateLike').addClass(result.viewClass);
				$('#reviewLikeCnt').text(result.likeCnt);
			}
		}, 'json');
	}
};