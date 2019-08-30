/**
 * 이웃 리스트 출력
 */
function printBlogFollowList(page, result) { // 팔로우, 팔로워 리스트 출력
	if (result == "" || result == null) {
		$(".tab-content").html(page + "(이)가 없습니다.");
		return false;
	}

	var resultHtml = "<ul id='replyHtml' class='comment-list'>";
	for (var i = 0; i < result.length; i++) { // followerList
		resultHtml += "<li class='comment'>";
		resultHtml += "<div class='vcard img rounded-circle' style='background-image: url(" + result[i].member_profile + ");'></div>";
		resultHtml += "<div class='comment-body' style='padding-top:8px;'>";
		resultHtml += "<h3><a href='/blog/" + result[i].num_url + "'>" + result[i].member_nickname + "</a></h3>";

		if (page == "following") { // 팔로리스트
			resultHtml += "<p class='text'><span class='post-followicon' title='팔로우 취소' ";
			resultHtml += "onclick=\"followThisMember('" + result[i].member_nickname + "','" + result[i].num_url + "','언팔로우','');\">";
			resultHtml += "<i class='fas fa-user-minus' style='margin-left:0;'></i>&ensp;Unfollow</span></p>";
		} else { // 팔로워리스트
			if (result[i].follow_state == 2) { // 내가 팔로하는 이웃이면
				resultHtml += "<p class='text'>현재 팔로우중 입니다.</p>";
			} else {
				resultHtml += "<p class='text'><span class='post-followicon' title='팔로우 하기' ";
				resultHtml += "onclick=\"followThisMember('" + result[i].member_nickname + "','" + result[i].num_url + "','팔로우','');\">";
				resultHtml += "<i class='fas fa-user-plus' style='margin-left:0;'></i>&ensp;Follow</span></p>";
			}
		}

		resultHtml += "</div></li>";
	}

	$(".tab-content").html(resultHtml + "</ul>"); // 리스트 뿌리기
}