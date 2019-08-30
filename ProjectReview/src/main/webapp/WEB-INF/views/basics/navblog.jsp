<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="col-lg-3 sidebar">
	<div class="info-profile">
		<div class="info-profile-wrap align-items-center">
			<div class="img logo rounded-circle" style="background-image: url(${memberDTO.member_profile});">
				<c:if test="${MenuVisible == true}">
					<img src="/img/edit-profile.png" data-dismiss="modal" data-toggle="modal" data-target="#ModalProfileImg" title="프로필 업데이트">
				</c:if>
			</div>
			<div class="desc">
				<h4 class="profile-name">${memberDTO.member_nickname}
					<c:if test="${MenuVisible != true && not empty login}">
						<span class="insertFollowState"> <i class="fas fa-envelope ml-2" data-dismiss="modal" data-toggle="modal" data-target="#ModalSendMessage"
							onclick="showMessageModal('${memberDTO.member_num}', '${memberDTO.member_nickname}','')" title="메세지 보내기"></i> <script>
								followStateView("${memberDTO.num_url}");
							</script></span>
					</c:if>
				</h4>
				<span class="date"><i class="far fa-calendar-alt"></i>&ensp;Joined ${memberDTO.insertDateFormat()}</span>
			</div>
		</div>

		<table class="table table-follow">
			<tr>
				<td><span id="cnt_follow" <c:if test="${MenuVisible == true}">onclick="location.href='/blog/follow/following'" class="cnt_follow pnt"</c:if>>0</span>팔로우</td>
				<td><span id="cnt_follower" <c:if test="${MenuVisible == true}">onclick="location.href='/blog/follow/followers'" class="cnt_follow pnt"</c:if>>0</span>팔로워</td>
			</tr>
		</table>
	</div>

	<div class="sidebar-box">
		<div class="categories">
			<ul>
				<li><a href="/blog/${memberDTO.num_url}">블로그<i class="fas fa-angle-right"></i></a></li>
				<li><a href="/blog/guest/${memberDTO.num_url}">방명록<i class="fas fa-angle-right"></i></a></li>
			</ul>
		</div>
	</div>
</div>

<script>
	$.ajax({
		url : "/blog/side/count",
		type : "POST",
		dataType : "json",
		data : {
			check_num : "${memberDTO.num_url}"
		},
		success : function(result) {
			$("#cnt_follow").text(result.cnt1);
			$("#cnt_follower").text(result.cnt2);
		}
	});
</script>
<c:if test="${MenuVisible == true}"><jsp:include page="../modal/profile.jsp" /></c:if>
<c:if test="${not empty login && PageTitle != 'My Info'}"><jsp:include page="../modal/msgSend.jsp" /></c:if>