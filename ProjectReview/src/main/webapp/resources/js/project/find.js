/**
 * 회원정보 찾기
 */
var regExpPassword = /(?=.*\d{1,50})(?=.*[~`!@#$%\^&*()-+=]{1,50})(?=.*[a-zA-Z]{2,50}).{8,50}$/;

$(document).ready(function() {
	$("#btn_userAccountFind").click(function() { // 입력한 정보가 DB에 있는지 확인하는 부분
		$.post("/account/search", {
			member_id : $("#member_id").val(),
			member_name : $("#member_name").val(),
			member_phone : $("#member_phone").val(),
			nowPage : location.href
		}, function(result) {
			if (result < 0) {
				alert("가입된 회원이 아닙니다.");
				return;
			}

			$("#member_name").attr("readonly", true); // 입력한 정보는 수정못하도록 설정
			$("#member_id").attr("readonly", true);
			$("#member_phone").attr("readonly", true);
			if (result == "") {
				alert("계정 확인이 되었습니다. 새 비밀번호를 입력해 주세요.");
				$(".userAccount_search").addClass("row");
				resultHtml = "<div class='col-12 col-md-4'><label>New Password</label></div>";
				resultHtml += "<div class='col-12 col-md-8'><input type='password' id='newPassword' class='form-control newPwd mb-4' placeholder='NEW PASSWORD' autofocus></div>";
				resultHtml += "<div class='col-12 col-md-4'><label>New Password Check</label></div>";
				resultHtml += "<div class='col-12 col-md-8'><input type='password' id='newPasswordCheck' class='form-control newPwd' placeholder='NEW PASSWORD CHECK'></div>";
				resultHtml += "<div class='col-12 mt-3 text-center btn_userAccount_search'><button type='button' class='btn' onclick='userAccountUpdatePw();'>비밀번호 변경</button></div>";
			} else {
				resultHtml = "회원님의 등록된 계정은 <span class='findData'>" + result + "</span> 입니다.";
			}
			$(".btn_userAccount_search").html("");
			$(".userAccount_search").addClass("p-4 bg-light text-center");
			$(".userAccount_search").html(resultHtml);
		}, 'json').fail(function(jqXHR) {
			alert("미입력된 정보가 있습니다. 모두 입력해 주세요.");
		});
	});
});

function userAccountUpdatePw() { // 비밀번호 찾기 > 등록된 계정일 경우
	var updatePw = $("#newPassword").val();
	var updatePw2 = $("#newPasswordCheck").val();

	if (regExpPassword.test(updatePw) == false) {
		alert("비밀번호는 숫자,특문,영문을 포함하여 8자리 이상 입력해 주세요.");
		$("#newPassword").focus();
		return false;
	}

	$.post("/account/newpw", {
		member_pw : updatePw,
		member_pw2 : updatePw2,
		member_id : $("#member_id").val(),
		member_phone : $("#member_phone").val()
	}, function(result) {
		if (result == 0) {
			alert("비밀번호가 일치하지 않습니다. 다시 입력해 주세요.");
			$("#newPasswordCheck").val("");
			$("#newPasswordCheck").focus();
		} else {
			alert("비밀번호가 변경되었습니다. 로그인 화면으로 이동합니다.");
			location.href = "/account/login";
		}
	}, 'json').fail(function(jqXHR) {
		alert("미입력된 정보가 있습니다. 모두 입력해 주세요.");
	});
}