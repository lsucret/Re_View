/**
 * 회원관리
 */
$(document).ready(function() {
	$('<script src="//cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.1/jquery.validate.min.js"><\/script>').appendTo(document.body);
});

window.onload = function() {
	$.validator.addMethod("pwdMatch", function(pwd, element) {
		return pwd.match(/^(?=.*[a-zA-Z])(?=.*[0-9]).{8,12}$/);
	}, "비밀번호는 숫자와 영문자 조합으로 8~12자리를 사용해야 합니다.");
	$.validator.addMethod("phoneMatch", function(phone, element) {
		return phone.match(/^\d{3}-\d{3,4}-\d{4}$/);
	}, "휴대폰번호가 올바르지 않습니다. 다시 입력해 주세요.");

	// --- 회원가입
	$("#frmJoin").validate({
		focusCleanup : false, // true이면 잘못된 필드에 포커스가 가면 에러를 지움
		focusInvalid : true, // 유효성 검사 후, 재입력 해야하는 input창에 포커스 허용
		onkeypress : true,
		rules : {
			member_id : {
				required : true,
				email : true,
				remote : {
					url : "/member/idDuplicateCheck",
					type : "post",
					data : {
						member_id : function() {
							return $("#member_id").val();
						}
					}
				}
			},
			member_pw : {
				required : true,
				pwdMatch : true
			},
			member_pw2 : {
				required : true,
				equalTo : "input[name='member_pw']"
			},
			member_name : {
				required : true
			},
			member_nickname : {
				required : true
			},
			member_phone : {
				required : true,
				phoneMatch : true,
				remote : {
					url : "/member/phoneDuplicateCheck",
					type : "post",
					data : {
						member_phone : function() {
							return $("#member_phone").val();
						}
					}
				}
			}
		},
		messages : {
			member_id : {
				required : "아이디를 입력해 주세요.",
				email : "이메일 형식이 올바르지 않습니다.",
				remote : "이미 등록된 계정입니다. 다시 입력해 주세요."
			},
			member_pw : {
				required : "비밀번호를 입력해 주세요."
			},
			member_pw2 : {
				required : "비밀번호 확인을 입력해 주세요.",
				equalTo : "비밀번호가 일치하지 않습니다."
			},
			member_name : {
				required : "이름을 입력해 주세요."
			},
			member_nickname : {
				required : "닉네임을 입력해 주세요."
			},
			member_phone : {
				required : "휴대폰번호를 입력해 주세요.",
				remote : "이미 등록된 휴대폰 번호입니다. 다시 입력해 주세요."
			}
		},
		submitHandler : function(form) {
			if (!$("#allowpersonalinfo").is(":checked")) {
				alert("개인정보 수집에 동의해 주세요.");
				return false;
			}
			alert("회원가입이 완료되었습니다:)");
			form.submit();
		}
	});

	// --- 회원정보수정
	$("#frmModifyInfo").validate({
		focusInvalid : true,
		onkeypress : true,
		rules : {
			member_name : {
				required : true
			},
			member_nickname : {
				required : true
			},
			member_phone : {
				required : true,
				phoneMatch : true,
				remote : {
					url : "/member/phoneDuplicateCheck",
					type : "post",
					data : {
						member_phone : function() {
							return $('input[name=member_phone]').val();
						}
					}
				}
			}
		},
		messages : {
			member_name : {
				required : "이름을 입력해 주세요."
			},
			member_nickname : {
				required : "닉네임을 입력해 주세요."
			},
			member_phone : {
				required : "휴대폰번호를 입력해 주세요.",
				remote : "이미 등록된 휴대폰 번호입니다. 다시 입력해 주세요."
			}
		},
		submitHandler : function(form) {
			$.post("/member/modify", {
				member_name : $('input[name=member_name]').val(),
				member_nickname : $('input[name=member_nickname]').val(),
				member_phone : $('input[name=member_phone]').val(),
				section : "info"
			}, function(result) {
				if (result == -1) {
					alert("변경할 데이터가 없습니다.");
				} else {
					alert("회원정보 수정이 완료되었습니다:)");
					location.reload();
				}
			}, 'json');
		}
	});

	// --- 비밀번호변경
	$("#frmModifyPwd").validate({
		focusInvalid : true,
		onkeypress : true,
		rules : {
			old_pw : {
				required : true
			},
			member_pw : {
				required : true,
				pwdMatch : true
			},
			member_pw2 : {
				required : true,
				equalTo : "input[name='member_pw']"
			}
		},
		messages : {
			old_pw : {
				required : "기존 비밀번호를 입력해 주세요."
			},
			member_pw : {
				required : "새 비밀번호를 입력해 주세요."
			},
			member_pw2 : {
				required : "비밀번호 확인을 입력해 주세요.",
				equalTo : "비밀번호가 일치하지 않습니다."
			}
		},
		submitHandler : function(form) {
			$.post("/member/modify", {
				old_pw : $('input[name=old_pw]').val(),
				new_pw : $('input[name=member_pw]').val(),
				section : "pwd"
			}, function(result) {
				if (result == -2) {
					alert("기존 비밀번호가 일치하지 않습니다.");
				} else if (result == -1) {
					alert("변경할 데이터가 없습니다.");
				} else {
					alert("비밀번호 변경이 완료되었습니다:)");
					location.reload();
				}
			}, 'json');
		}
	});
};