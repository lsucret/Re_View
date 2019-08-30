/**
 * 리뷰 상세페이지
 */
$(document).ready(function() {
	$("head").append("<script src='/js/kakao/kakaoShare.js'><\/script>");
	$("head").append("<script src='/js/project/review.js'><\/script>");
	$("#kakao-share-url").val(window.location.href);
	
	$(".btn-review-modify").click(function() {
		$(".review-cog-box").stop().fadeToggle(500);
		$(".review-cog-box").css("display", "inline");
		return false;
	});

	$('html').click(function(e) {
		if (!($(e.target).hasClass("review-cog-box") || $(e.target).hasClass("review-cog-box-li"))) {
			$(".review-cog-box").stop().fadeOut(500);
		}
	});
});

function deleteReview() {
	var sendData = window.location.href;
	if (confirm("해당 게시글을 삭제 하시겠습니까?")) {
		$.post("/blog/review/delete", {
			sendData : sendData
		}, function(result) {
			if (result.result > 0) {
				alert("게시글이 삭제 되었습니다.");
				location.replace(result.sendURL);
			}
		}, 'json');
	}
}

// --------- 리뷰 댓글 ---------
var htmls = "";
function selectReviewReplyList(num, memberNum, url, page) { // 댓글 불러오기
	$.post("/blog/review/reply/count", {
		reviewNum : num
	}, function(result) {
		$("#cCnt").html(result);
		
		if (result > 0) {
			if (page == 1) {
				htmls = "";
			}

			$.getJSON("/blog/review/reply/" + num + "?page=" + page, function(result) {
				var listlen = result.length;
				for (var i = 0; i < listlen; i++) {
					htmls += "<li class='comment'>";
					htmls += "<div class='vcard img rounded-circle' style='background-image: url(" + result[i].member_profile + ");'></div>"; // 프사
					htmls += "<div class='comment-body'>";

					if (result[i].member_num != memberNum) {
						htmls += "<h3><a href='/blog/" + result[i].num_url + "' title='블로그 바로가기'>" + result[i].member_nickname + "</a>";
					} else {
						htmls += "<h3>" + result[i].member_nickname;
					}
					htmls += "<span class='comment-modi ml-3'>";

					if (result[i].member_num == memberNum) { // 자신의 글에만 수정,삭제 버튼 활성화
						htmls += "<i class='far fa-edit' title='댓글 수정' data-toggle='modal'" + "data-target='#ModalReviewReplyModify' ";
						htmls += "onclick=\"setModalReply('" + result[i].comment_num + "','" + result[i].comment_content + "')\"></i>";
						htmls += "<i class='far fa-trash-alt' title='댓글 삭제' onclick='deleteReply(" + result[i].comment_num + "," + num + "," + memberNum + ",\"" + url + "\")'></i>";
					}
					htmls += "</span></h3><div class='meta'>" + result[i].comment_updateDate + "</div><p class='text'>" + result[i].comment_content + "</p></div></li>";
				}
				$("#replyHtml").html(htmls);
			});

			var btnHTML = "";
			if (result > Number(page) * 15) {
				btnHTML = "<button class='col-12 mt-3 btn-more rounded' onclick='selectReviewReplyList(\"" + num + "\",\"" + memberNum + "\",\"" + url + "\"," + (Number(page) + 1) + ")'>";
				btnHTML += "더 보기 (" + page + "/" + (parseInt(Number(result) / 15) + 1) + ")</button>";
			}
			$(".view-btn-more").html(btnHTML);

		}
	}, 'json');
}

function writeReply(num, memberNum, url) {
	var text = $("#comment_content").val()
	var msg = $("#comment_content").val().trim();
	var content = $("#comment_content").val();
	content = content.replace(/(?:\r\n|\r|\n)/g, "<br>");

	if (msg == "") {
		alert("내용을 입력해 주세요.");
		$("#comment_content").val("");
		$("#comment_content").focus();
		return;
	}

	$.ajax({
		url : "/blog/review/reply/" + num,
		type : "post",
		dataType : "text",
		data : JSON.stringify({ // String으로 변환해서 전송 (변수명(받는쪽) : 데이터(보내는쪽))
			comment_content : content
		}),
		contentType : "application/json; charset=UTF-8",
		success : function(result) {
			if (result == "SUCCESS") {
				alert('등록되었습니다.');
				selectReviewReplyList(num, memberNum, url, '1');
				$("#comment_content").val("");
			}
		}
	});
}

function setModalReply(num, content) { // 모달창으로 전환시 comment_num과 comment_content 넘겨줌
	var content = content.replace(/<br>/gi, '\r\n');
	$('#comment_num').val(num);
	$('#replyText').val(content); // 수정창으로 원댓글 복사
}

function modifyReply(num, memberNum, url) { // 댓글 수정
	var commentNum = $('#comment_num').val();
	var commentContent = $('#replyText').val();
	var text = $("#replyText").val().trim();
	commentContent = commentContent.replace(/(?:\r\n|\r|\n)/g, '<br>');

	if (text == "") {
		alert("내용을 입력해 주세요.");
		$('#replyText').focus();
		return;
	}

	$.ajax({
		url : "/blog/review/reply/" + commentNum,
		type : "put",
		contentType : "application/json",
		dataType : "text",
		data : JSON.stringify({ // String으로 변환해서 전송 (변수명(받는쪽) : 데이터(보내는쪽))
			comment_content : commentContent
		}),
		success : function(result) {
			if (result == "SUCCESS") {
				alert("수정되었습니다.");
				$('#replyText').val("");
				$("#ModalReviewReplyModify").modal("hide"); // Modal 닫기
				selectReviewReplyList(num, memberNum, url, '1');
			}
		}
	});
}

function deleteReply(commentNum, num, memberNum, url) { // 댓글 삭제
	if (confirm("해당 댓글을 삭제하시겠습니까?")) {
		$.ajax({
			url : "/blog/review/reply/" + commentNum,
			type : "delete",
			dataType : "text",
			data : "commentNum",
			success : function(data) {
				if (data == "SUCCESS") {
					alert('삭제되었습니다.');
					selectReviewReplyList(num, memberNum, url, '1');
				}
			}
		});
	}
}