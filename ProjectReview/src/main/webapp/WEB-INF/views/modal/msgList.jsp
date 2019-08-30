<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="ModalMessageBox" class="modal fade" data-backdrop="static" data-keyboard="false">
	<div class="modal-dialog modal-lg contact-modal">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title">메세지함</h4>
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			</div>
			<ul class="nav nav-tabs messageBoxTab">
			</ul>
			<div class="modal-body" style="min-height: 300px;">
				<div class="row">
					<div class="col-lg-12">
						<div class="tab-pane fade show" id="#receiveM">
							<div class="accordion receiveBoxDisplay" id="accordionA"></div>
						</div>
						<div class="tab-pane fade show" id="#sendM">
							<div class="accordion sendBoxDisplay" id="accordionB"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<jsp:include page="../modal/msgSend.jsp" />