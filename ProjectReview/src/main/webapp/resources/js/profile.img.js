/**
 * 프로필 사진 업로드
 */
$(document).ready(function() {
	$("#sendNowPage").val(location.href);
});

function fileValidationCheck() { // 프로필 변경
	var profileImg = document.getElementById("profileImg");
	if (document.getElementById("profileImg").value != "") {
		var fileSize = document.profileFrm.profileImg.files[0].size;
		var maxSize = 3 * 1024 * 1024; // 3MB
		var ext = $('#profileImg').val().split('.').pop().toLowerCase();

		if (fileSize > maxSize) { // 용량체크
			alert("이미지 크기는 3MB 이내로 등록 가능합니다. ");
			$("#profileImg").val("");
			return false;
		}

		if ($.inArray(ext, [ 'gif', 'png', 'jpg', 'jpeg' ]) == -1) { // 확장자 체크
			alert('GIF, PNG, JPG, JPEG 파일인지 확인하세요.');
			$("#profileImg").val(""); // input file 파일명을 다시 지워준다.
			return false;
		}
	}
	profilePreviewChgFn(profileImg);
}

function profilePreviewChgFn(input) {
	if (input.files && input.files[0]) {
		var reader = new FileReader();
		reader.onload = function(e) {
			$('#profilePreview').attr('src', e.target.result);
		}
		reader.readAsDataURL(input.files[0]);
	}
}

function profileSubmitFn() {
	var hasProfile = $("#profileImg").val();
	if (hasProfile.length > 0) {
		alert("프로필 사진을 변경합니다.");
		$("#profileFrm").submit();
	}
}

function profileDelete() {
	if (confirm("프로필 사진을 삭제하시겠습니까?")) {
		$.ajax({
			url : "/member/profileDelete",
			type : "POST",
			dataType : "JSON",
			success : function(result) {
				if (result > 0) {
					location.reload();
				}
			}
		});
	}
}

$(function() {
	$("#profileUpdateDiv").on("dragover", function(e) { // 프로필 이미지 드래그 앤 드롭으로 업로드
		e.preventDefault();
		$("#profileUpdateDiv").attr("style", "border: 5px solid #FE6F5E;"); // 드래그 표시
	}).on("dragleave drop", function(e) {
		e.preventDefault();
		$("#profileUpdateDiv").removeAttr("style", "border: 5px solid #FE6F5E;");
	});

	$("#profileFrm").on("dragover drop", function(e) {
		e.preventDefault();
	}).on("drop", function(e) { // 파일을 해당 엘리먼트에 넣습니다.
		$("input[type='file']").prop("files", e.originalEvent.dataTransfer.files);
		fileValidationCheck();
	});
});