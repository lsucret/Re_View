/**
 * 메인페이지
 */
$(document).ready(function() {
	$.ajax({
		url : "/timeline/main",
		type : "POST",
		dataType : "json",
		success : function(result) {
			var resultHTML = "";
			if (result.loginNum > 0) {
				var list = result.timelineList;
				if (list.length > 0) {
					for (var i = 0; i < list.length; i++) {
						if (i < 3) {
							resultHTML += '<div class="col-12">';
							if (list[i].alert == 1) {
								resultHTML += '<img src="/img/alert_new.png" class="alert-new">';
							}
							resultHTML += '<div class="blog-entry main-blog d-flex mb-3">';
							resultHTML += '<a href="javascript:goReviewDetailFn(\'' + list[i].num_url + '\',\'' + list[i].review_num + '\')" class="block-20 rounded"';
							resultHTML += ' style="width: 40%; height: 170px; background-image: url(' + list[i].title_img + ');" title="리뷰 상세보기"></a>';
							resultHTML += '<div class="text d-block" style="width: 60%; margin-left: 10px;"><div class="post_text_1 text-left">';
							resultHTML += '<p class="heading" onclick="goReviewDetailFn(\'' + list[i].num_url + '\',\'' + list[i].review_num + '\')" title="리뷰 상세보기">';
							resultHTML += '<font style="color: ' + list[i].title_color + ';white-space: nowrap;">';
							resultHTML += '<img src="/img/star' + list[i].review_grade + '.png" class="review_icon">' + list[i].review_title + '</font>';
							resultHTML += '</p><i class="fas fa-map-marker-alt"></i>' + list[i].store_name;
							resultHTML += '<span class="d-block content-preview">' + removeTag(list[i].review_content) + '</span>';
							resultHTML += '<p class="writer_data"><span class="img list-profile rounded-circle" style="background-image: url(' + list[i].member_profile + ');"></span> '
							resultHTML += '<a href="/blog/' + list[i].num_url + '" title="블로그 바로가기">' + list[i].member_nickname + '</a> / ' + list[i].review_insertDate + '</p>';
							resultHTML += '</div></div></div></div>';
						} else {
							resultHTML += "<a style='float: right;' href='/timeline/list'>이웃 새 글 더 보기</a>";
						}
					}
				} else {
					resultHTML = "<span class='d-block rounded pt-5 pb-5' style='border: 1px dashed #CFD0C4;'>이웃 새 글이 없습니다.</span>";
				}
			} else {
				resultHTML = "<span class='d-block rounded pt-5 pb-5' style='border: 1px dashed #CFD0C4;'>로그인 후, 이웃들의 새 글을 받아보세요!<br>";
				resultHTML += "<button type='button' onclick='location.href=\"/account/login\"' class='btn mt-3 ml-0'>로그인</button></span>";
			}

			$(".timelineList").html(resultHTML);
		}
	});
})

function searchUser() { // 이웃검색
	var inputData = $("#searchUser").val();
	if (inputData == "") {
		alert("검색할 데이터가 없습니다.");
	} else {
		$.ajax({
			type : "POST",
			url : "/search/user",
			dataType : "json",
			data : {
				searchUser : inputData
			},
			success : function(result) {
				$("#searchUserModal").modal("show");

				var list = result.userList;
				var resultHtml = "";

				if (list.length == 0) {
					$(".userList").html("검색 결과가 없습니다.");
					return;
				}

				for (var i = 0; i < list.length; i++) {
					resultHtml += "<li class='comment' style='padding-top: 10px;'>";
					resultHtml += "<div class='vcard img rounded-circle' style='background-image: url(" + list[i].member_profile + ");margin-bottom: 10px;'></div>";
					resultHtml += "<div class='comment-body' style='padding: 18px 0;'>";
					resultHtml += "<h3 class='d-inline'><a href='/blog/" + list[i].num_url + "'>" + list[i].member_nickname + " (" + list[i].member_id.split("@")[0] + ")</a></h3>";

					if (result.loginNum > 0) { // 로그인 상태
						if (list[i].follow_state > 0 || list[i].follow_state != null) { // follow상태
							resultHtml += "<p class='text d-inline ml-3 main" + list[i].num_url + "'><span class='post-followicon' title='팔로우 취소' ";
							resultHtml += "onclick=\"followThisMember('" + list[i].member_nickname + "','" + list[i].num_url + "','언팔로우','main');\">";
							resultHtml += "<i class='fas fa-user-minus' style='margin-left:0;'></i>&ensp;Unfollow</span></p>";
						} else { // unfollow상태
							resultHtml += "<p class='text d-inline ml-3 main" + list[i].num_url + "'><span class='post-followicon' title='팔로우 하기' ";
							resultHtml += "onclick=\"followThisMember('" + list[i].member_nickname + "','" + list[i].num_url + "','팔로우','main');\">";
							resultHtml += "<i class='fas fa-user-plus' style='margin-left:0;'></i>&ensp;Follow</span></p>";
						}
					}

					resultHtml += '</div></li>';
				}
				$(".userList").html(resultHtml);
			}
		});
	}
}