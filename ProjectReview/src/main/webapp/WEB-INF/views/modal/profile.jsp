<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<div id="ModalProfileImg" class="modal fade">
	<div class="modal-dialog modal-lg contact-modal">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="exampleModalLongTitle">프로필 업데이트</h5>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>

			<div class="modal-body">
				<form action="/member/profileUpload" method="post" enctype="multipart/form-data" id="profileFrm" name="profileFrm">
					<div class="profile-update mb-3">
						<div class="profile-img rounded-circle mb-3" id="profileUpdateDiv">
							<img src="${login.member_profile}" id="profilePreview" onclick="document.all.profileImg.click();" title="클릭 또는 마우스로 파일을 끌어오세요.">
						</div>
						<div class="col-12">
							<button type="button" class="btn" onclick="profileSubmitFn()">프로필 수정</button>
							<c:if test="${fn:contains(login.member_profile, 'IMG_')}">
								<button type="button" class="btn" onclick="profileDelete()">프로필 삭제</button>
							</c:if>
							<input type="file" class="form-control" id="profileImg" name="profileImg" onchange="fileValidationCheck()"><input type="hidden" id="sendNowPage" name="sendNowPage">
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
<script src="/js/profile.img.js"></script>