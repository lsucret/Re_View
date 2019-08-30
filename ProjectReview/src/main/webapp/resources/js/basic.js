/**
 * 디폴트값
 */
$(document).ready(function() {
	var nowPage = window.location.pathname;

	// --- 헤더 메세지 리스트 (Limit 5)
	newMessageAlert();
	$('.view-msg').on('click', function() {
		$.getJSON("/message/list?tab=receive&message_read=0", function(result) {
			var resultHTML = "", listlen = result.length; // 안읽은 메세지 리스트 수
			var strMore = '메세지함 바로가기 <i class="fa fa-angle-right"></i>';
			if (listlen == 0) { // 안읽은 메세지가 없음 -> 리스트도 없음
				resultHTML = '<li class="text-center pt-2 pb-3 font-weight-bold">새로운 메세지가 없습니다.</li>';
			} else {
				for (var i = 0; i < listlen; i++) {
					resultHTML += '<li class="mb-2 pb-2 bb-1">';
					resultHTML += '<span class="img list-profile rounded-circle" style="background-image: url(' + result[i].member_profile + ');"></span> ';
					resultHTML += '<a href="/blog/' + result[i].num_url + '" class="font-weight-bold" title="블로그 바로가기">' + result[i].member_nickname + '</a>';
					resultHTML += '<span style="float: right;font-size: .8em;color: #888;">' + result[i].message_sendDate + '</span>';
					resultHTML += '<span class="d-block text-ellipsis pnt" onclick="showMessageBox(\'' + result[i].message_num + '\');">' + result[i].message_title + '</span></li></div>';
					if (i > 3) { // 리스트가 이미 5개 나옴(i==4) 그 뒤의 데이터는 더보기를 통해 메세지함으로 넘어갈 수 있도록 버튼 추가
						strMore = '메세지 더 보기 <i class="fa fa-angle-right"></i>';
						break;
					}
				}
			}

			resultHTML += '<li class="text-center"><span class="d-block pnt" onclick="showMessageBox(\'\');">' + strMore + '</span></li>';
			$(".jq-dropdown-menu").html(resultHTML);
			newMessageAlert();
		});
	});

	// --- 사이드바
	if (nowPage != "/") {
		$(".pageTitle-sub").text(nowPage.split("/")[1]);
		var viewIcon = "";
		if (nowPage.indexOf("blog/map") > 0) {
			nowPage = nowPage.replace("map", "list");
			$(".btn-blogmain-sidebar").html("<a href='" + nowPage + "' class='rounded-left' title='리스트로 보기'><i class='fas fa-list'></i></a>");
		} else if (nowPage.indexOf("blog/list") > 0) {
			nowPage = nowPage.replace("list", "map");
			$(".btn-blogmain-sidebar").html("<a href='" + nowPage + "' class='rounded-left' title='맵으로 보기'><i class='fas fa-map-marked-alt'></i></a>");
		}
	}

	$('#dismiss, .overlay').on('click', function() {
		$('#sidebar').removeClass('active');
		$('.overlay').removeClass('active');
		$('body').css('overflow', '');
	});

	$('#sidebarCollapse').on('click', function() {
		$('#sidebar').addClass('active');
		$('.overlay').addClass('active');
		$('.collapse.in').toggleClass('in');
		$('a[aria-expanded=true]').attr('aria-expanded', 'false');
		$('body').css('overflow', 'hidden');
	});
});

function removeTag(str) {
	return str.replace(/(<([^>]+)>)/gi, "");
}

function findCategoryVal(category) {
	$('#category').val(category);
	$('#searchForm').submit();
}

function goReviewDetailFn(url, num) { // 리뷰 조회수
	$.post("/blog/review/detail/hitCount", {
		num_url : url,
		review_num : num
	}, function(result) {
		location.href = result;
	}, 'json');
}

/* 이웃관리 */
function followStateView(url) { // 이웃 상태 출력
	$.post("/blog/follow/view", {
		blogger_url : url
	}, function(result) {
		var viewHTML = "<i class='fas fa-user-", memberDTO = result.memberDTO;
		if (result.fstate == 1) { // follow상태
			viewHTML += "minus ml-2' title='팔로우 취소' onclick=\"followThisMember('" + memberDTO.member_nickname + "','" + memberDTO.num_url + "','언팔로우','')\"></i>";
		} else { // unfollow상태
			viewHTML += "plus ml-2' title='팔로우 하기' onclick=\"followThisMember('" + memberDTO.member_nickname + "','" + memberDTO.num_url + "','팔로우','')\"></i>";
		}
		$(".insertFollowState").append(viewHTML);
	}, 'json');
}

function followThisMember(nick, num, str, view) { // 이웃 추가 여부
	if (confirm(nick + "님을 " + str + " 하시겠습니까?")) {
		$.post("/blog/follow/update", {
			blogger_url : num,
			followStr : str
		}, function(result) {
			if (result > 0) {
				alert(nick + "님을 " + str + " 합니다.");
				if (view == "") {
					location.reload();
				} else { // 메인화면 유저 검색 결과에 보여질 부분
					var resultHTML = "<span class='post-followicon' title='";
					if (str == "팔로우") {
						resultHTML += "팔로우 취소' onclick='followThisMember(\"" + nick + "\",\"" + num + "\",\"언팔로우\",'');'>";
						resultHTML += "<i class='fas fa-user-minus' style='margin-left:0;'></i>&ensp;Unfollow";
					} else {
						resultHTML += "팔로우 하기' onclick='followThisMember(\"" + nick + "\",\"" + num + "\",\"팔로우\",'');'>";
						resultHTML += "<i class='fas fa-user-plus' style='margin-left:0;'></i>&ensp;Follow";
					}
					resultHTML += "</span>";
					$(".main" + num).html(resultHTML);
				}
			}
		}, 'json');
	}
}

/* 메세지 */
function showMessageModal(memberNum, nickname, list) { // 메세지 전송 모달. list:리스트 새로고침 여부
	$(".sendMessageBtn").attr("onclick", "sendMessage('" + list + "')");
	$('#memberNum').val(memberNum);
	$('#inputName').val("TO. " + nickname);
	$('#messageTitle, #messageContent').val("");
	$('#ModalSendMessage').modal("show");
}

function sendMessage(list) { // 메세지 전송
	var content = $('#messageContent');
	if (content.val().trim() == "") {
		alert("내용을 입력해 주세요.");
		content.focus();
		return;
	}

	$.ajax({
		url : "/message/send",
		type : "post",
		contentType : "application/json",
		timeout : 3000,
		dataType : "text",
		data : JSON.stringify({
			message_title : $("#messageTitle").val(),
			message_content : content.val(),
			message_receiver : $('#memberNum').val()
		}),
		success : function(data) {
			if (data == "SUCCESS") {
				alert("메세지를 전송하였습니다.");
				$('#messageTitle, #messageContent').val("");
				$('#ModalSendMessage').modal("hide");
				if (list != '') {
					viewMessageList('send', '');
				}
			}
		}
	});
};

function showMessageBox(data) { // 메세지함
	$('#ModalMessageBox').modal("show");
	var viewTab = '<li class="nav-item"><a class="nav-link active" data-toggle="tab" href="#receiveM" onclick="viewMessageList(\'receive\',\'\')">받은메세지</a></li>';
	viewTab += '<li class="nav-item"><a class="nav-link" data-toggle="tab" href="#sendM" onclick="viewMessageList(\'send\',\'\')">보낸메세지</a></li>';
	$(".messageBoxTab").html(viewTab);
	viewMessageList('receive', data);
	newMessageAlert();
}

function viewMessageList(tab, data) { // 메세지 리스트 불러오기. data는 tab이름, nav는 안읽은메세지 limit5 체크
	var hiddenBox = "sendBoxDisplay", viewBox = "receiveBoxDisplay"; // 디폴트 - 받은메세지함
	if (tab == "send") { // 보낸메세지함일때
		hiddenBox = "receiveBoxDisplay";
		viewBox = "sendBoxDisplay";
	}
	$("." + hiddenBox).empty();

	$.getJSON("/message/list?tab=" + tab, function(result) {
		var resultHTML = "", listlen = result.length;
		for (var i = 0; i < listlen; i++) {
			resultHTML += '<div class="card"><div class="card-header" id="headingOne">';
			resultHTML += '<span class="img list-profile rounded-circle" style="background-image: url(' + result[i].member_profile + ');"></span> ';
			resultHTML += '<a href="/blog/' + result[i].num_url + '" class="font-weight-bold" title="블로그 바로가기">' + result[i].member_nickname + '</a>';
			resultHTML += '<span style="float: right;font-size: .8em;color: #888;">' + result[i].message_sendDate + '</span>';
			resultHTML += '<span id="msg-title' + result[i].message_num + '" class="d-block text-ellipsis pnt collapsed';
			var viewIcon = "far fa-envelope"; // 메세지 아이콘
			if (result[i].message_read == 1 || data == result[i].message_num) {
				viewIcon += "-open"; // 읽은 메세지면 오픈아이콘
			} else {
				resultHTML += " font-weight-bold"; // 안읽은 메세지는 제목 굵게
			}
			resultHTML += '" data-toggle="collapse" data-target="#collapse' + i + '" aria-expanded="false" onclick="viewMessageDetail(' + result[i].message_num + ', \'' + tab + '\')">';
			resultHTML += '<i class="' + viewIcon + '" id="envelopeR' + result[i].message_num + '"></i>&ensp;' + result[i].message_title + '</span></div>';
			resultHTML += '<div id="collapse' + i + '" class="messageDetail' + result[i].message_num + ' collapse" aria-labelledby="headingOne" data-parent="#accordion"></div></div></div>';
		}
		$("." + viewBox).html(resultHTML);
		if (data != "") { // 안읽은메세지 limit5에서 클릭한 경우, 메세지 상세 바로 출력
			$('.messageDetail' + data).collapse();
			viewMessageDetail(data, "receive");
		}
	});
}

function viewMessageDetail(messageNum, page) { // 메세지 상세
	var detailCheck = $(".messageDetail" + messageNum).attr("class");
	if (detailCheck.indexOf("show") < 0) {
		$.post("/message/detail", {
			message_num : messageNum,
			page_tab : page
		}, function(result) {
			var iconHTML = '&ensp;<i class="far fa-comment-alt" title="메세지 보내기" onclick=\'showMessageModal("' + result.message_receiver + '","' + result.member_nickname + '","list")\'></i>';
			if (page == "receive") { // 받은메세지
				$("#msg-title" + messageNum).removeClass("font-weight-bold");
				if ($("#envelopeR" + messageNum).attr("class") == "far fa-envelope") {
					$('#envelopeR' + messageNum).attr('class', 'far fa-envelope-open');
				}
				iconHTML = '&ensp;<i class="fas fa-reply" title="답변하기" onclick=\'showMessageModal("' + result.message_sender + '","' + result.member_nickname + '","")\'></i>';
			}

			var resultHTML = '<div class="card-body p-0"><div class="media"><div class="media-body">';
			resultHTML += '<span class="d-block pr-3 pl-3">' + result.message_content + '</span>';
			resultHTML += '<p class="mt-2 mb-2 mr-3">';
			if (result.message_readDate == null) {
				resultHTML += '읽지않음';
			} else {
				resultHTML += result.message_readDate + ' 에 읽음';
			}
			resultHTML += iconHTML;
			resultHTML += '&ensp;<i class="far fa-trash-alt" title="메세지 삭제" onclick=\'messageDelete("' + result.message_num + '","' + page + '")\'></i>';
			resultHTML += '</p></div></div></div>';

			$(".messageDetail" + messageNum).html(resultHTML);
			newMessageAlert();
		}, 'json');
	}
}
// 메세지 삭제
function messageDelete(messageNum, delChk) {
	var strUrl = "/message/delete/0/", alertMsg = "해당 메세지를 삭제하시겠습니까?";
	if (delChk == "send") { // 보낸메세지에서 삭제
		strUrl = "/message/delete/1/";
		alertMsg = "메세지를 삭제하여도 상대방 메세지함에서는 삭제되지 않습니다. 해당 메세지를 삭제하시겠습니까?";
	}

	if (confirm(alertMsg)) {
		$.ajax({
			url : strUrl + messageNum,
			type : "put",
			dataType : "text",
			success : function(data) {
				if (data == "SUCCESS") {
					alert('삭제되었습니다.');
					viewMessageList(delChk, '');
				}
			}
		});
	}
}

function newMessageAlert() {
	$.post("/message/new/count", function(result) {
		$(".badge").html(result);
	}, 'json');
}