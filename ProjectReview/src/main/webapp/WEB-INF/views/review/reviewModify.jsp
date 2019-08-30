<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../basics/header.jsp" />
<script type="text/javascript" src="/smarteditor/js/HuskyEZCreator.js" charset="utf-8"></script>
<script>
	var editor_object = []; // 전역변수선언

	$(document).ready(function() { // DB에서 작성글을 불러와서 셋팅
		$("head").append("<script src='/js/project/review.js'><\/script>");
		$.ajax({ // 카테고리 출력
			url : "/blog/review/category",
			type : "POST",
			dataType : "json",
			success : function(result) {
				for (var i = 0; i < result.length; i++) {
					$("select").append("<option id='"+result[i].category_code+"' value='" + result[i].category_code + "'>" + result[i].category_name + "</option>");
				}
				$('#store_category').val("${review.category_code}"); // db select 값 입력
			}
		});

		nhn.husky.EZCreator.createInIFrame({
			oAppRef : editor_object,
			elPlaceHolder : "smarteditor",
			sSkinURI : "/smarteditor/SmartEditor2Skin.html",
			htParams : {
				bUseToolbar : true,
				bUseVerticalResizer : true,
				bUseModeChanger : false
			},
			fOnAppLoad : function() {
				editor_object.getById["smarteditor"].exec("PASTE_HTML", [ '${review.review_content}' ]); //내용밀어넣기
			}
		});

		$(".btn_reviewModify").click(function() { // 전송버튼 클릭이벤트   
			editor_object.getById["smarteditor"].exec("UPDATE_CONTENTS_FIELD", []); //textarea 내용을 에디터로 넣음         
			/* var content = $.trim($("#smarteditor").val()); */
			if ($("#review_title").val().trim() == "") {
				alert("제목을 입력해 주세요.");
				$('#review_title').focus();
			}
			if ($("#store_name").val() == "") {
				alert("장소를 입력해 주세요.");
				$('#store_name').focus();
			}
			$.ajax({
				type : "POST",
				url : "/blog/review/modify",
				dataType : "text",
				data : $("form[name=frmModify]").serialize(),
				success : function(result) {
					alert("리뷰 게시글이 수정되었습니다.");
					location.replace("/blog/review/detail/${login.num_url}/${review.review_num}");
				}
			});
		});
	});
</script>

<section class="ftco-section bg-light">
	<div class="container rounded">
		<div class="blog-details-area">
			<div class="row">
				<div class="col-12">
					<div class="about-info p-4 bg-light mb-2">리뷰 타이틀은 첫 이미지로 자동 등록됩니다. 업로드 할 사진이 없을 경우, 기본이미지로 보여집니다.</div>

					<div class="contact-form mb-5 mb-lg-0">
						<form name="frmModify" id="frmModify" method="post" accept-charset="utf-8">
							<div class="row">
								<div class="col-12 mb-4">
									<input type="text" name="review_title" id="review_title" class="form-control contact-input-title" value="${review.review_title}" placeholder="Review Title" maxlength="40" autofocus>
								</div>
								<div class="col-12 col-lg-2">
									<label>카테고리</label>
								</div>
								<div class="col-12 col-lg-10 mb-4">
									<select name="store_category" id="store_category" class="form-control">
									</select>
								</div>
								<div class="col-12 col-lg-2">
									<label>리뷰장소</label>
								</div>
								<div class="col-12 col-lg-10 mb-4">
									<input type="text" name="store_name" id="store_name" class="form-control" value="${review.store_name}" data-toggle="modal" data-target="#searchStoreModal" readonly>
								</div>
								<div class="col-12 col-lg-2">
									<label>리뷰별점</label>
								</div>
								<div class="col-12 col-lg-10 mb-4" style="height: 45px;">
									<input type="hidden" id="selected_rating" name="review_grade" value="${review.review_grade}">
									<c:forEach begin="1" end="5" var="index">
										<button type="button" class="btnrating btn-default" data-attr="${index}" id="rating-star-${index}">
											<i class="<c:if test="${index <= review.review_grade}">fas</c:if><c:if test="${index > review.review_grade}">far</c:if> fa-star"></i>
										</button>
									</c:forEach>
									&ensp;<i class="fas fa-sync-alt" onclick="resetReviewGrade()" title="별점 초기화"></i>
								</div>
								<div class="col-12">
									<textarea name="review_content" id="smarteditor" class="form-control rounded mb-4"></textarea>
								</div>

								<input type="hidden" name="store_info" id="store_info" value="${review.store_addr}###${review.store_lat}###${review.store_lng}"> <input type="hidden" name="review_num"
									value="${review.review_num}">

								<div class="col-12 text-right">
									<button type="button" class="btn btn_reviewModify">Modify</button>
									<button type="button" class="btn btn-bg-white" onclick="window.history.go(-1);">Back</button>
								</div>
							</div>
						</form>

					</div>
				</div>
			</div>
		</div>
	</div>
</section>

<!-- 가게 검색 -->
<jsp:include page="../modal/searchStore.jsp" />

<jsp:include page="../basics/footer.jsp" />