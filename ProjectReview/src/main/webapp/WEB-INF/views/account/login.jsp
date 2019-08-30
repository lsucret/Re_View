<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="../basics/header.jsp" />

<script type="text/javascript">
	function noBack() {
		window.history.forward();
	}

	$(document).ready(function() {
		window.history.forward();

		$("#loginBtn").click(function() {
			var userId = $("#id").val();
			var userPw = $("#password").val();
			if (userId == "") {
				alert("아이디를 입력하세요.");
				$("#id").focus(); // 입력포커스 이동
				return false;
			}
			if (userPw == "") {
				alert("비밀번호를 입력하세요.");
				$("#password").focus();
				return false;
			}
		});
	});
</script>

<section class="ftco-section bg-light">
	<div class="container rounded">
		<div class="row">
			<div class="col-12">
				<div class="contact-form">
					<ul class="nav nav-tabs">
						<li class="nav-item"><a class="nav-link active" href="/account/login">로그인</a></li>
						<li class="nav-item"><a class="nav-link" href="/member/join">회원가입</a></li>
					</ul>

					<div class="tab-content">
						<form name="frm_login" class="member-data-form" action="${path}/account/loginPost" method="post">
							<div class="row">
								<div class="col-12 col-md-3">
									<label>아이디</label>
								</div>
								<div class="col-12 col-md-9">
									<input type="text" id="id" name="member_id" class="form-control mb-4" placeholder="ID (E-mail)" autofocus>
								</div>
								<div class="col-12 col-md-3">
									<label>비밀번호</label>
								</div>
								<div class="col-12 col-md-9">
									<input type="password" id="password" name="member_pw" class="form-control mb-4" placeholder="PASSWORD">
								</div>

								<div class="col-12 mt-3 text-center">
									<button type="submit" id="loginBtn" class="btn">계정 로그인</button>
								</div>
							</div>
						</form>
					</div>

					<div class="text-right">
						<button type="button" class="btn btn-bg-white" onclick="location.href='/account/find/id'">
							<i class="fas fa-search"></i> 계정찾기
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>

<jsp:include page="../basics/footer.jsp" />