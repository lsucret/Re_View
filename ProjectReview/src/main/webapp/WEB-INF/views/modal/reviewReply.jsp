<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- 댓글 수정 모달창 -->
<div id="ModalReviewReplyModify" class="modal fade">
	<div class="modal-dialog modal-lg contact-modal">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="exampleModalLongTitle">댓글 수정</h5>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<div class="form-group">
					<textarea id="replyText" name="replyText" class="form-control rounded textarea-update" placeholder="내용 입력" autofocus></textarea>
					<input type="hidden" id="comment_num">
				</div>
			</div>
			<div class="modal-footer">
				<input type="button" class="btn" value="댓글 수정" onclick="modifyReply('${review.review_num}', '${login.member_num}', '${login.num_url}')">
			</div>
		</div>
	</div>
</div>