package com.project.review.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.review.dto.MemberDTO;
import com.project.review.service.GuestReplyService;

/**
 * 방명록댓글
 */

@Controller
@RequestMapping("/blog/guest/reply/*")
public class GuestReplyController {

	@Inject
	GuestReplyService replyService;

	@RequestMapping(value = "list", method = RequestMethod.POST)
	@ResponseBody
	public List<HashMap<String, Object>> ReplyList(HttpServletRequest request, HttpSession session) throws Exception {
		String strGuestNum = request.getParameter("guest_num") + "";
		return replyService.selectGuestReplyList(Integer.parseInt(strGuestNum), 0);
	}

	/* 방명록 댓글 등록 */
	@RequestMapping(value = "write", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> ReplyWrite(HttpServletRequest request, HttpSession session) {
		String content = request.getParameter("content") + "";
		if (content.trim().equals("")) {
			return null;
		}

		MemberDTO memberDTO = (MemberDTO) session.getAttribute("login");
		int intReplyNum = replyService.insertGuestReply(Integer.parseInt(request.getParameter("guest_num") + ""), memberDTO.getMember_num(), content);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("replyDTO", replyService.selectGuestReplyList(0, intReplyNum).get(0));
		map.put("total", replyService.selectGuestReplyTotalCount(0, intReplyNum));
		return map;
	}

	/* 방명록 댓글 수정 */
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> ReplyEditContent(HttpServletRequest request, HttpSession session) throws Exception {
		int intReplyNum = Integer.parseInt(request.getParameter("reply_num") + "");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("replyDTO", replyService.selectGuestReplyList(0, intReplyNum).get(0));
		return map;
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> ReplyUpdate(HttpServletRequest request, HttpSession session) {
		String content = request.getParameter("content") + "";
		if (content.trim().equals("")) {
			return null;
		}

		int intReplyNum = Integer.parseInt(request.getParameter("reply_num") + "");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", replyService.updateGuestReply(content, intReplyNum));
		return map;
	}

	/* 방명록 댓글 삭제 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> ReplyDelete(HttpServletRequest request, HttpSession session) {
		int intReplyNum = Integer.parseInt(request.getParameter("reply_num") + "");
		int guestNum = replyService.selectGuestNumFromReply(intReplyNum);
		replyService.deleteGuestReply(intReplyNum);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("replyCount", replyService.selectGuestReplyTotalCount(guestNum, 0));
		map.put("guestNum", guestNum);
		return map;
	}
}