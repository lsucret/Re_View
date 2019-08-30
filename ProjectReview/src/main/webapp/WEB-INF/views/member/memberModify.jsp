<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../basics/header.jsp" />

<section class="ftco-section">
	<div class="container rounded">
		<div class="row">
			<div class="col-lg-9">
				<div class="col-12">
					<ul class="nav nav-tabs mb-5">
						<li class="nav-item"><a class="nav-link active" href="#">회원정보수정</a></li>
						<li class="nav-item"><a class="nav-link" href="/blog/setup">블로그설정</a></li>
					</ul>
					<div class="about-info p-4 bg-light">&bull;&ensp;표시는 필수항목이오니 반드시 입력해 주시기 바랍니다.</div>
					<div class="text-right mb-5">
						<span class="general-font-size14">최종수정일 ${memberDTO.updateDateFormat()}</span>
					</div>

					<!-- 회원정보 수정 -->
					<form id="frmModifyInfo" method="post">
						<div class="row pb-5 mb-5 bb-1">
							<div class="col-12 col-lg-3">
								<label>아이디</label>
							</div>
							<div class="col-12 col-lg-9 mb-3">
								<input type="text" class="form-control font-weight-bold" value="${memberDTO.member_id}" readonly>
							</div>
							<div class="col-12 col-lg-3">
								<label>&bull;성명</label>
							</div>
							<div class="col-12 col-lg-9 mb-3">
								<input type="text" name="member_name" class="form-control" value="${memberDTO.member_name}" placeholder="NAME" maxlength="8" autofocus>
							</div>
							<div class="col-12 col-lg-3">
								<label>&bull;닉네임</label>
							</div>
							<div class="col-12 col-lg-9 mb-3">
								<input type="text" name="member_nickname" class="form-control" value="${memberDTO.member_nickname}" placeholder="NICKNAME" maxlength="8">
							</div>
							<div class="col-12 col-lg-3">
								<label>&bull;휴대폰번호</label>
							</div>
							<div class="col-12 col-lg-9 mb-3">
								<input type="text" name="member_phone" class="form-control" value="${memberDTO.member_phone}" placeholder="XXX-XXXX-XXXX" maxlength="13">
							</div>

							<div class="col-12 text-right">
								<button type="submit" class="btn">회원정보수정</button>
								<button type="button" class="btn btn-bg-white" onclick="if (confirm('회원 탈퇴 시, 등록된 데이터도 모두 삭제됩니다. 탈퇴하시겠습니까?')) {location.href='/account/leave'}">회원탈퇴</button>
							</div>
						</div>
					</form>

					<!-- 비밀번호 수정 -->
					<form id="frmModifyPwd" method="post">
						<div class="row mb-5">
							<div class="col-12 col-lg-3">
								<label>비밀번호</label>
							</div>
							<div class="col-12 col-lg-9 mb-3">
								<input type="password" name="old_pw" class="form-control" placeholder="PASSWORD">
							</div>
							<div class="col-12 col-lg-3">
								<label>새 비밀번호</label>
							</div>
							<div class="col-12 col-lg-9 mb-3">
								<input type="password" name="member_pw" class="form-control" placeholder="숫자와 영문자 조합으로 8~12자리를 사용" maxlength="12">
							</div>
							<div class="col-12 col-lg-3">
								<label>새 비밀번호확인</label>
							</div>
							<div class="col-12 col-lg-9 mb-3">
								<input type="password" name="member_pw2" class="form-control" placeholder="NEW PASSWORD CHECK" maxlength="12">
							</div>

							<div class="col-12 text-right">
								<button type="submit" class="btn">비밀번호변경</button>
							</div>
						</div>
					</form>
				</div>
			</div>

			<!-- 사이드 --><jsp:include page="../basics/navblog.jsp" />
		</div>
	</div>
</section>

<script src="/js/project/member.js"></script>
<jsp:include page="../basics/footer.jsp" />