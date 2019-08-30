<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../basics/header.jsp" />

<script>
	$(document).ready(function() {
		var page = "${nowPageData}";
		if (page == 'page_findId') {
			$("#findpw").removeClass("active");
			$("#findid").addClass("active");
		} else if (page == 'page_findPw') {
			$("#findid").removeClass("active");
			$("#findpw").addClass("active");
		}
	});
</script>

<section class="ftco-section bg-light">
	<div class="container rounded">
		<div class="row">
			<div class="col-12">
				<div class="contact-form">
					<ul class="nav nav-tabs">
						<li class="nav-item"><a class="nav-link active" id="findid" href="#" onclick="location.replace('/account/find/id')">아이디 찾기</a></li>
						<li class="nav-item"><a class="nav-link" id="findpw" href="#" onclick="location.replace('/account/find/pw')">비밀번호 찾기</a></li>
					</ul>

					<div class="tab-content">
						<div class="about-info p-4 bg-light mb-5">등록된 휴대폰 번호로 조회 가능합니다.</div>

						<div class="row member-data-form">
							<c:if test="${nowPageData == 'page_findId'}">
								<div class="col-12 col-md-3">
									<label>Name</label>
								</div>
								<div class="col-12 col-md-9">
									<input type="text" name="member_name" id="member_name" class="form-control mb-4" placeholder="NAME" autofocus>
								</div>
							</c:if>
							<c:if test="${nowPageData == 'page_findPw'}">
								<div class="col-12 col-md-3">
									<label>ID (E-mail)</label>
								</div>
								<div class="col-12 col-md-9">
									<input type="text" name="member_id" id="member_id" class="form-control mb-4" placeholder="ID (E-mail)" autofocus>
								</div>
							</c:if>
							<div class="col-12 col-md-3">
								<label>Phone</label>
							</div>
							<div class="col-12 col-md-9">
								<input type="tel" name="member_phone" class="form-control mb-4" id="member_phone" placeholder="PHONE">
							</div>
							<div class="col-12 mt-3 text-center btn_userAccount_search">
								<button type="button" id="btn_userAccountFind" class="btn">계정 찾기</button>
							</div>
						</div>

						<!-- 결과 출력 부분 -->
						<div class="userAccount_search"></div>

					</div>

					<div class="mt-3 text-right">
						<button type="button" class="btn btn-bg-white" onclick="location.href='/account/login'">로그인</button>
						<button type="button" class="btn btn-bg-white" onclick="location.href='/member/join'">회원가입</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>

<script src="/js/project/find.js"></script>
<jsp:include page="../basics/footer.jsp" />