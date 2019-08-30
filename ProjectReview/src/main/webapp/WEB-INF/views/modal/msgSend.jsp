<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="ModalSendMessage" class="modal fade" data-backdrop="static" data-keyboard="false" style="z-index: 1051">
	<div class="modal-dialog modal-lg contact-modal">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title">메세지 전송</h4>
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-12">
						<input type="hidden" id="memberNum"><input type="text" class="form-control font-weight-bold" id="inputName" readonly> <input type="text" class="form-control"
							id="messageTitle" placeholder="메세지 제목 입력" maxlength="100" autofocus>
						<textarea class="form-control mt-3 rounded" id="messageContent" placeholder="메세지 내용 입력"></textarea>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<input type="button" class="btn sendMessageBtn" value="메세지 전송">
			</div>
		</div>
	</div>
</div>