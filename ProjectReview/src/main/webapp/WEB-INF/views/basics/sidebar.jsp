<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" href="/css/sidebar.css">
<div class="wrapper">
	<nav id="sidebar">
		<div id="dismiss">
			<i class="fas fa-arrow-right"></i>
		</div>
		<div class="sidebar-header">
			<h5>
				반갑습니다<br> <br> <span class="img header-profile rounded-circle" style="background-image: url(${login.member_profile});"></span>&ensp;<a href="/member/modify" title="회원정보 바로가기"
					style="display: inline-block;">${login.member_nickname}</a>&ensp;님 :)
			</h5>
		</div>
		<ul class="list-unstyled components">
			<li><a href="/timeline/list"><i class="fas fa-stream"></i> 타임라인</a></li>
			<li><a href="#blogSubmenu" data-toggle="collapse" aria-expanded="true"><i class="fab fa-blogger-b"></i> MY BLOG</a>
				<ul class="collapse list-unstyled show" id="blogSubmenu">
					<li class="pl-4"><a href="/blog/${login.num_url}">블로그 홈</a></li>
					<li class="pl-4"><a href="/blog/review/write">리뷰작성</a></li>
					<li class="pl-4"><a href="/blog/follow/following">이웃목록</a></li>
					<li class="pl-4"><a href="/blog/guest/${login.num_url}">방명록</a></li>
					<li class="pl-4"><a href="/blog/review/heart/list">좋아요내역</a></li>
				</ul></li>
			<li><a href="#setSubmenu" data-toggle="collapse" aria-expanded="false"><i class="fas fa-cog"></i> 설정</a>
				<ul class="collapse list-unstyled" id="setSubmenu">
					<li class="pl-4"><a href="/member/modify">회원정보수정</a></li>
					<li class="pl-4"><a href="/blog/setup">블로그설정</a></li>
				</ul></li>
			<li><a href="#" onclick="if (confirm('로그아웃 하시겠습니까?')) {location.href='/account/logout'}"><i class="fas fa-sign-out-alt"></i> 로그아웃</a></li>
		</ul>
	</nav>
</div>