<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="/js/kakao/searchStore.js"></script>

<div id="searchStoreModal" class="modal fade">
	<div class="modal-dialog modal-lg contact-modal">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="exampleModalLongTitle">리뷰장소 검색</h5>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<div id="menu_wrap" class="rounded">
					<div class="option">
						<form onsubmit="searchPlaces(); return false;">
							<div class="col-12">
								<input type="text" name="searchStore" id="searchStore" class="b-1 mb-2 rounded" placeholder="Search Store">
							</div>
							<div class="col-12">
								<button type="submit" class="btn btn-block btn-bg-white" style="margin: 0">Search</button>
							</div>
						</form>
					</div>
					<ul id="placesList"></ul>
				</div>
			</div>
			<div class="modal-footer">
				<div id="pagination"></div>
			</div>
		</div>
	</div>
</div>