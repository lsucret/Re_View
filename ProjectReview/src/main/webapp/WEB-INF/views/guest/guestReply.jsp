<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="card-header p-0 mb-3" id="heading${guest.guest_num}">
	<button class="btn btn-link collapsed" data-toggle="collapse" data-target="#collapseComment${guest.guest_num}" aria-expanded="false" aria-controls="collapse"
		onclick="guestReplyList('${guest.guest_num}','${login.num_url}')">
		댓글<span class="spanCnt" id="replyCount${guest.guest_num}"> (${guest.reply_count})</span>
	</button>
</div>
<div id="collapseComment${guest.guest_num}" class="collapse" aria-labelledby="heading${guest.guest_num}">
	<ul class="children" id="children${guest.guest_num}"></ul>
	<c:if test="${not empty login}">
		<div class="card-body p-0">
			<textarea name="message" id="guestReply_content${guest.guest_num}" class="form-control rounded" placeholder="댓글입력란"></textarea>
			<div class="text-right">
				<input type="button" class="btn btn-bg-white mt-2 mb-3" value="댓글 등록" onclick="guestReplyWrite('${guest.guest_num}');">
			</div>
		</div>
	</c:if>
</div>