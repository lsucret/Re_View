package com.project.review.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.review.dto.MemberDTO;
import com.project.review.service.FollowService;
import com.project.review.service.FollowStateService;
import com.project.review.service.MemberDefService;

/**
 * 이웃관리 - 리스트 출력, 이웃등록 및 취소, 블로그 사이드 바에 이웃 수 출력
 */

@Controller
@RequestMapping("/blog/follow/*")
public class FollowController {

	@Inject
	MemberDefService defService;
	@Inject
	FollowService followService;
	@Inject
	FollowStateService stateService;

	/* 이웃목록 */
	@RequestMapping(value = "{page}")
	public String followMain(@PathVariable String page, Model model, HttpSession session) throws Exception {
		model.addAttribute("memberDTO", (MemberDTO) session.getAttribute("login"));
		model.addAttribute("nowPageData", page);
		model.addAttribute("MenuVisible", true);
		model.addAttribute("PageTitle", "Follow");
		return "follow/followList";
	}

	/* 이웃목록 출력 */
	@RequestMapping(value = "{page}/list", method = RequestMethod.POST)
	@ResponseBody
	public List<MemberDTO> followList(@PathVariable String page, HttpServletRequest request, HttpSession session) throws Exception {
		MemberDTO loginDTO = (MemberDTO) session.getAttribute("login");
		List<MemberDTO> memberList = null;

		if (page.equals("following")) {
			memberList = followService.selectFollowList(loginDTO.getMember_num());
		} else if (page.equals("followers")) {
			memberList = followService.selectFollowerList(loginDTO.getMember_num());
		}

		for (int i = 0; i < memberList.size(); i++) {
			int result = stateService.viewFollowState(loginDTO.getMember_num(), memberList.get(i).getMember_num());
			if (result > 0) {
				memberList.get(i).setFollow_state(2);
			}
		}

		return memberList;
	}

	/* 이웃등록 여부 체크 */
	@RequestMapping(value = "view", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> followStateView(HttpServletRequest request, HttpSession session) throws Exception {
		MemberDTO memberDTO = (MemberDTO) session.getAttribute("login");
		int bloggerNum = defService.selectMemerNumDecrypt(request.getParameter("blogger_url").toString());
		int fstate = stateService.viewFollowState(memberDTO.getMember_num(), bloggerNum);

		String result = "unFollow";
		if (fstate == 1) {
			result = "Follow";
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberDTO", defService.selectMemberNum(bloggerNum));
		map.put("result", result);
		map.put("fstate", fstate);
		return map;
	}

	/* 이웃 등록 및 취소 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public int followUpdate(HttpServletRequest request, HttpSession session) throws Exception {
		MemberDTO loginDTO = (MemberDTO) session.getAttribute("login");
		return followService.followNunfollow(loginDTO.getMember_num(), request.getParameter("blogger_url"), request.getParameter("followStr"));
	}
}