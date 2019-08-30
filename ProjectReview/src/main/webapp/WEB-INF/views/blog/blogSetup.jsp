<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="../basics/header.jsp" />

<script>
	$(document).ready(function() {
		var mainData = "${memberDTO.blog_main}";

		if (mainData.indexOf("list") > 0) {
			$("#blog_main1").prop("checked", true);
			$(".preview1").css("filter", "none");
			$(".img1").addClass("mainSelect");
		} else {
			$("#blog_main2").prop("checked", true);
			$(".preview2").css("filter", "none");
			$(".img2").addClass("mainSelect");
		}

		$(".img-preview").click(function() {
			var classVal = $(this).attr("class");
			if (classVal.indexOf("mainSelect") < 0) {
				if (confirm("변경하시겠습니까?")) {
					$.ajax({
						type : "POST",
						url : "/blog/setup/update",
						dataType : "json",
						data : {
							updateData : classVal
						}
					});
					alert("변경되었습니다.");
					location.reload();
				}
			}
		});
	});
</script>
<section class="ftco-section">
	<div class="container rounded">
		<div class="row">
			<div class="col-lg-9">
				<div class="row d-flex">
					<div class="col-12">
						<ul class="nav nav-tabs mb-5">
							<li class="nav-item"><a class="nav-link" href="/member/modify">회원정보수정</a></li>
							<li class="nav-item"><a class="nav-link active" href="#">블로그설정</a></li>
						</ul>
						<div class="about-info p-4 bg-light">블로그 메인화면을 설정할 수 있습니다.</div>

						<form action="#" method="post">
							<div class="row blog-main-check mt-5">
								<div class="col-md-6 pb-4" title="리스트로 보기">
									<div class="img-preview rounded img1">
										<img src="/img/preview1.png" class="preview1">
									</div>
								</div>
								<div class="col-md-6 pb-4" title="맵으로 보기">
									<div class="img-preview rounded img2">
										<img src="/img/preview2.png" class="preview2">
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>

			<!-- 사이드 --><jsp:include page="../basics/navblog.jsp" />
		</div>
	</div>
</section>

<jsp:include page="../basics/footer.jsp" />