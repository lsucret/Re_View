/**
 * 방명록 관리
 */
window.onload = function() {
	$(".btn_guestWrite").click(function() { // 방명록 등록
		$.post("/blog/guest/write", {
			content : $("#guest_content").val(),
			viewURL : location.href,
			hidden_check : $("#insert_hidden").is(":checked")
		}, function(result) {
			if (result == 0) {
				alert("내용을 입력해 주세요.");
				$("#guest_content").focus();
			} else {
				alert("방명록이 등록되었습니다.");
				location.reload();
			}
		}, 'json');
	});

	$(".viewModalContent").click(function() { // 방명록 수정 버튼 눌렀을 때
		guestContentEdit($(this).data("id")); // 기존 등록되어있던 내용 출력하는 부분
	});
}

function guestContentEdit(num) { // 방명록 수정 시, 기존 등록된 내용 보여주는 부분 (num=guest방명록 번호)
	$.post("/blog/guest/edit", {
		guest_num : num
	}, function(result) {
		var viewHtml = "<input type='checkbox' value='1' id='update_hidden' ";
		if (result.hidden_check == 1) {
			viewHtml += "checked";
		}
		viewHtml += "> <label for='update_hidden' style='cursor: pointer;'>비밀글</label>";
		viewHtml += "<input type='button' class='btn' style='float: right;' value='방명록 수정' onclick=\"guestDataUpdate('guest','" + num + "');\">";
		$("#guest_content_edit").val(result.guest_content);
		$("#guest_content_edit").focus();
		$(".modal-footer-guest").html(viewHtml);
	}, 'json');
}

function guestReplyList(num, url) { // 댓글 리스트 출력 (num=방명록 번호, url=회원url)
	$.post("/blog/guest/reply/list", {
		guest_num : num
	}, function(result) {
		var viewContent = "";
		for (var i = 0; i < result.length; i++) {
			viewContent += "<li class='comment' id='guestReply_delete" + result[i].reply_num + "'><div class='comment-content d-flex'>";
			viewContent += "<div class='vcard img rounded-circle' style='background-image: url(" + result[i].member_profile + ");'></div><div class='comment-body'><h3>";

			if (url == result[i].num_url) { // 내가 등록한 글이면
				viewContent += result[i].member_nickname;
				viewContent += "<span class='reply_modify_group" + result[i].reply_num + "'><i class='far fa-edit' ";
				viewContent += "onclick=\"guestReplyContentEdit('" + result[i].reply_num + "','" + num + "');\" title='방명록 댓글 수정'></i><i";
				viewContent += " class='far fa-trash-alt' onclick=\"guestDataDelete('reply','" + result[i].reply_num + "');\" title='방명록 댓글 삭제'></i></span>";
				viewContent += "<span class='reply_modify_cancel' id='reply_modify_cancel" + result[i].reply_num + "'></span>";
			} else {
				viewContent += "<a href='/blog/" + result[i].num_url + "' title='블로그 바로가기'>" + result[i].member_nickname + "</a>";
			}

			viewContent += "</h3><div class='meta'>" + result[i].reply_insertDate + "</div>";
			viewContent += "<p class='text guestReplyContent" + result[i].reply_num + "'>" + result[i].reply_content + "</p></div></li>";
		}
		$("#children" + num).html(viewContent);
		$("#guestReply_content" + num).focus();
	}, 'json');
}

function guestReplyWrite(num) { // 방명록 댓글 등록 (num=방명록 번호)
	var replyContent = $("#guestReply_content" + num);
	$.post("/blog/guest/reply/write", {
		content : replyContent.val(),
		guest_num : num
	}, function(result) {
		alert("댓글이 등록 되었습니다.");
		var reply = result.replyDTO;
		var viewContent = "<li class='comment' id='guestReply_delete" + reply.reply_num + "'><div class='comment-content d-flex'>";
		viewContent += "<div class='vcard img rounded-circle' style='background-image: url(" + reply.member_profile + ");'></div>";
		viewContent += "<div class='comment-body'><h3>" + reply.member_nickname + "<span class='reply_modify_group" + reply.reply_num + "'><i class='far fa-edit'";
		viewContent += " onclick=\"guestReplyContentEdit('" + reply.reply_num + "','" + reply.guest_num + "');\" title='방명록 댓글 수정'></i>";
		viewContent += "<i class='far fa-trash-alt' onclick=\"guestDataDelete('reply','" + reply.reply_num + "');\" title='방명록 댓글 삭제'></i></span>";
		viewContent += "<span id='reply_modify_cancel" + reply.reply_num + "'></span></h3><div class='meta'>" + reply.reply_insertDate + "</div>";
		viewContent += "<p class='text guestReplyContent" + reply.reply_num + "'>" + reply.reply_content + "</p></div></div></li>";
		$("#children" + num).append(viewContent);
		$("#guestReply_content" + num).val("");
		$("#replyCount" + num).html(" (" + result.total + ")");
	}, 'json').fail(function(jqXHR) {
		alert("내용을 입력해 주세요.");
		replyContent.focus();
	});
}

function guestReplyContentEdit(num) { // 방명록 댓글 수정 (num=방명록 댓글 번호)
	$.post("/blog/guest/reply/edit", {
		reply_num : num
	}, function(result) {
		var reply = result.replyDTO;
		var resultHtml = "<textarea id='reply_content_edit" + num + "' class='form-control rounded' placeholder='댓글 내용'>" + reply.reply_content + "</textarea>";
		resultHtml += "<div class='text-right'><input type='button' class='btn btn-bg-white mt-2' value='댓글 수정' onclick=\"guestDataUpdate('reply','" + num + "');\"></div>";
		$(".guestReplyContent" + num).html(resultHtml);
		$("#reply_content_edit" + num).focus();
		$(".reply_modify_group" + num).css("display", "none");
		$("#reply_modify_cancel" + num).html("<i class='far fa-times-circle' title='취소'onclick=\"guestReplyContentEditCancel('" + num + "');\"></i>");
	}, 'json').fail(function(jqXHR) {
		alert("내용을 입력해 주세요.");
		$("#guestReply_content" + num).focus();
	});
}

function guestReplyContentEditCancel(num) { // 수정 취소 (num=방명록 댓글 번호)
	$.post("/blog/guest/reply/edit", {
		reply_num : num
	}, function(result) {
		$("p.guestReplyContent" + num).html(result.replyDTO.reply_content);
		$(".reply_modify_group" + num).css("display", "");
		$("#reply_modify_cancel" + num).empty();
	}, 'json');
}

function guestDataUpdate(page, num) { // 방명록 또는 댓글 업데이트 (page=페이지, num=번호)
	var focusContent = $("#guest_content_edit");
	var sendURL = "/blog/guest/update";
	var sendData = {
		content : focusContent.val(),
		member_id : '${ViewUserURL}',
		hidden_check : $("#update_hidden").is(":checked"),
		guest_num : num
	};

	if (page == "reply") { // 댓글 수정
		focusContent = $("#reply_content_edit" + num);
		sendURL = "/blog/guest/reply/update";
		sendData = {
			content : focusContent.val(),
			reply_num : num
		};
	}

	$.post(sendURL, sendData, function(result) {
		alert("수정되었습니다.");
		if (page == "guest") {
			location.reload();
		} else {
			$(".guestReplyContent" + num).html(focusContent.val());
			$(".reply_modify_group" + num).css("display", "");
			$("#reply_modify_cancel" + num).empty();
		}
	}, 'json').fail(function(jqXHR) {
		alert("내용을 입력해 주세요.");
		focusContent.focus();
	});
}

function guestDataDelete(page, num) { // 방명록, 방명록 댓글 삭제 (page=페이지, num=번호)
	var sendURL = "/blog/guest/", sendData = "";
	if (page == "guest") { // 방명록 삭제
		sendURL += "delete";
		sendData = {
			guest_num : num
		};
	} else if (page == "reply") { // 방명록 댓글 삭제
		sendURL += "reply/delete";
		sendData = {
			reply_num : num
		};
	}

	if (confirm("해당 글을 삭제 하시겠습니까?")) {
		$.post(sendURL, sendData, function(result) {
			alert("삭제되었습니다.");
			if (page == "guest") { // 방명록 삭제
				location.reload();
			} else {
				$("#guestReply_delete" + num).remove();
				$("#replyCount" + result.guestNum).html(" (" + result.replyCount + ")");
			}
		}, 'json');
	}
}