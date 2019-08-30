<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../basics/header.jsp" />
<script type="text/javascript" src="/smarteditor/js/HuskyEZCreator.js" charset="utf-8"></script>
<script>
	$(document).ready(function() {
		$("head").append("<script src='/js/project/review.js'><\/script>");
		$.ajax({ // 카테고리 출력
			url : "/blog/review/category",
			type : "POST",
			dataType : "json",
			success : function(result) {
				for (var i = 0; i < result.length; i++) {
					$("select").append("<option id='"+result[i].category_code+"' value='" + result[i].category_code + "'>" + result[i].category_name + "</option>");
				}
			}
		});

		$(function() {
			var editor_object = []; // 전역변수선언
			nhn.husky.EZCreator.createInIFrame({
				oAppRef : editor_object,
				elPlaceHolder : "smarteditor",
				sSkinURI : "/smarteditor/SmartEditor2Skin.html",
				htParams : {
					// 툴바 사용 여부 (true:사용/ false:사용하지 않음)
					bUseToolbar : true,
					// 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
					bUseVerticalResizer : true,
					// 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
					bUseModeChanger : false,
				}
			});

			$(".btn_reviewWrite").click(function() { // 전송버튼 클릭이벤트
				editor_object.getById["smarteditor"].exec("UPDATE_CONTENTS_FIELD", []); // id가 smarteditor인 textarea에 에디터 대입

				$.ajax({
					type : "POST",
					url : "/blog/review/write",
					dataType : "json",
					data : $("form[name=frmWrite]").serialize(),
					success : function(result) {
						if (result.result == "notitle") {
							alert("제목을 입력해 주세요.");
							$("input[name='review_title']").focus();
						} else if (result.result == "nocategory") {
							alert("카테고리를 선택해 주세요.");
							$("#store_category").focus();
						} else if (result.result == "noname") {
							alert("리뷰장소를 선택해 주세요.");
							$("input[name='store_name']").focus();
						} else {
							alert("리뷰 게시글이 등록되었습니다.");
							location.replace(result.result);
						}
					}
				});
			})
		})
	});
</script>

<section class="ftco-section bg-light">
	<div class="container rounded">
		<div class="blog-details-area">
			<div class="row">
				<div class="col-12">
					<div class="about-info p-4 bg-light mb-2">리뷰 타이틀은 첫 이미지로 자동 등록됩니다. 업로드 할 사진이 없을 경우, 기본이미지로 보여집니다.</div>

					<div class="contact-form mb-lg-0">
						<form name="frmWrite" id="frmWrite" method="post" accept-charset="utf-8">
							<div class="row">
								<div class="col-12 mb-4">
									<input type="text" name="review_title" class="form-control contact-input-title" placeholder="Review Title" maxlength="40" autofocus>
								</div>
								<div class="col-12 col-lg-2">
									<label>카테고리</label>
								</div>
								<div class="col-12 col-lg-10 mb-4">
									<select name="category_num" id="store_category" class="form-control">
										<option value="">카테고리 선택</option>
									</select>
								</div>
								<div class="col-12 col-lg-2">
									<label>리뷰장소</label>
								</div>
								<div class="col-12 col-lg-10 mb-4">
									<input type="text" name="store_name" id="store_name" class="form-control" placeholder="Click! Search Store" data-toggle="modal" data-target="#searchStoreModal" readonly>
								</div>
								<div class="col-12 col-lg-2">
									<label>리뷰별점</label>
								</div>
								<div class="col-12 col-lg-10 mb-4" style="height: 45px;">
									<input type="hidden" id="selected_rating" name="review_grade" value="0">
									<c:forEach begin="1" end="5" var="index">
										<button type="button" class="btnrating btn-default" data-attr="${index}" id="rating-star-${index}">
											<i class="far fa-star"></i>
										</button>
									</c:forEach>
									&ensp;<i class="fas fa-sync-alt" onclick="resetReviewGrade()" title="별점 초기화"></i>
								</div>
								<div class="col-12">
									<textarea name="review_content" id="smarteditor" class="form-control rounded mb-4"></textarea>
								</div>
								<input type="hidden" name="store_info" id="store_info">

								<div class="col-12 text-right">
									<button type="button" class="btn btn_reviewWrite">리뷰등록</button>
									<button type="button" class="btn btn-bg-white" onclick="window.history.go(-1);">취소</button>
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