<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<script src="//code.jquery.com/jquery-latest.min.js"></script>
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery-dropdown/2.0.3/jquery.dropdown.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/malihu-custom-scrollbar-plugin/3.1.5/jquery.mCustomScrollbar.concat.min.js"></script>
<!-- 카카오 -->
<script src="//dapi.kakao.com/v2/maps/sdk.js?appkey=34da3f8ea3cee4344a9e7d946456faf7&&libraries=services,clusterer"></script>

<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css">
<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/animate.css/3.7.2/animate.min.css">

<script src="/js/basic.js"></script>
<link rel="stylesheet" href="/css/style.css">
<link rel="icon" href="/img/favicon.png">
<script>
	jQuery.ajaxSetup({
		cache : false
	});
</script>
<title>Re:View</title>
</head>

<body class="animated fadeIn">
	<div class="overlay"></div>
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<div class="container">
			<a class="navbar-brand" href="/"><img src="/img/logo.png"></a>
			<div class="collapse navbar-collapse" id="navbarSupportedContent">
				<ul class="nav navbar-nav ml-auto">
					<c:if test="${empty login}">
						<li class="nav-item mr-3"><a href="/account/login" class="nav-link">LOG IN</a></li>
						<li class="nav-item"><a href="/member/join" class="nav-link">JOIN</a></li>
					</c:if>
					<c:if test="${not empty login}">
						<li class="nav-item mr-3"><a href="/timeline/list" class="nav-link">타임라인</a></li>
						<li class="nav-item mr-3"><a href="/blog/${login.num_url}" class="nav-link">블로그 홈</a></li>
						<li class="nav-item"><div id="jq-dropdown-msg" class="jq-dropdown jq-dropdown-tip jq-dropdown-anchor-right rounded">
								<ul class="jq-dropdown-menu rounded p-3"></ul>
							</div> <a href="#" class="jq-dropdown-msg view-msg" data-jq-dropdown="#jq-dropdown-msg"><i class="far fa-envelope mr-2" title="메시지 보기"></i><span class="badge rounded-circle"></span></a></li>
					</c:if>
				</ul>
			</div>

			<c:if test="${not empty login}">
				<button type="button" id="sidebarCollapse" class="ml-3" title="메뉴보기" style="background: transparent; border: 1px solid transparent;">
					<i class="fas fa-bars"></i>
				</button>
			</c:if>
		</div>
	</nav>

	<c:if test="${not empty PageTitle}">
		<section class="main-wrap">
			<div class="container rounded">
				<div class="row slider-text align-items-end">
					<div class="col-12 pb-5 text-center">
						<h1 class="mb-3 bread font-weight-bold">${PageTitle}</h1>
						<p class="breadcrumbs">
							<span class="mr-2"><a href="/">home</a></span><i class="fas fa-chevron-right mr-2" style="font-size: .7em;"></i><span class="pageTitle-sub"></span>
						</p>
					</div>
				</div>
			</div>
		</section>
	</c:if>