<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="../basics/header.jsp" />

<section class="ftco-section bg-light">
	<div class="container rounded">
		<div class="row">
			<div class="col-12">
				<div class="contact-form">
					<ul class="nav nav-tabs">
						<li class="nav-item"><a class="nav-link" href="/account/login">로그인</a></li>
						<li class="nav-item"><a class="nav-link active" href="/member/join">회원가입</a></li>
					</ul>

					<div class="tab-content">
						<div class="about-info p-4 bg-light mb-5">&bull;&ensp;표시는 필수항목이오니 반드시 입력해 주시기 바랍니다.&ensp;가입 후, 아이디는 수정하실 수 없으니 유의해주시기 바랍니다.</div>
						<form id="frmJoin" action="/member/join" class="member-join-form" method="post">
							<div class="row">
								<div class="col-12 col-md-3">
									<label>&bull;&ensp;아이디</label>
								</div>
								<div class="col-12 col-md-9 mb-4">
									<input type="text" id="member_id" name="member_id" class="form-control" placeholder="ID (E-mail)" autofocus>
								</div>
								<div class="col-12 col-md-3">
									<label>&bull;&ensp;비밀번호</label>
								</div>
								<div class="col-12 col-md-9 mb-4">
									<input type="password" name="member_pw" class="form-control" placeholder="숫자와 영문자 조합으로 8~12자리를 사용" maxlength="12">
								</div>
								<div class="col-12 col-md-3">
									<label>&bull;&ensp;비밀번호확인</label>
								</div>
								<div class="col-12 col-md-9 mb-4">
									<input type="password" name="member_pw2" class="form-control" placeholder="PASSWORD CHECK" maxlength="12">
								</div>
								<div class="col-12 col-md-3">
									<label>&bull;&ensp;성명</label>
								</div>
								<div class="col-12 col-md-9 mb-4">
									<input type="text" name="member_name" class="form-control" placeholder="NAME" maxlength="8">
								</div>
								<div class="col-12 col-md-3">
									<label>&bull;&ensp;닉네임</label>
								</div>
								<div class="col-12 col-md-9 mb-4">
									<input type="text" name="member_nickname" class="form-control" placeholder="NICKNAME" maxlength="8">
								</div>
								<div class="col-12 col-md-3">
									<label>&bull;&ensp;휴대폰번호</label>
								</div>
								<div class="col-12 col-md-9 mb-4">
									<input type="text" name="member_phone" id="member_phone" class="form-control" placeholder="XXX-XXXX-XXXX" maxlength="13">
								</div>

								<div class="col-12 text-center">
									<input type="checkbox" id="allowpersonalinfo"><label for="allowpersonalinfo" style="cursor: pointer;">개인정보 수집에 동의해 주세요.</label>
								</div>
								<div class="col-12 mt-3 text-center">
									<button type="submit" class="btn">회원가입</button>
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

<script src="/js/project/member.js"></script>
<jsp:include page="../basics/footer.jsp" />