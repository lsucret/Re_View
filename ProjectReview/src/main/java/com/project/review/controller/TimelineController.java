package com.project.review.controller;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.review.dto.MemberDTO;
import com.project.review.service.TimeLineService;

/**
 * 이웃 새 글
 */

@Controller
@RequestMapping("/timeline/*")
public class TimelineController {

	@Inject
	TimeLineService timelineService;

	/* 이웃 새 글 */
	@RequestMapping(value = "main", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> TimelineMain(HttpServletRequest request, HttpSession session) throws Exception {
		MemberDTO loginDTO = (MemberDTO) session.getAttribute("login");
		Map<String, Object> map = new HashMap<String, Object>();
		int member_num = 0;
		if (loginDTO != null) {
			member_num = loginDTO.getMember_num();
			map.put("timelineList", timelineService.timelineList(member_num, 0, 4));
		}
		map.put("loginNum", member_num);
		return map;
	}

	/* 한주동안의 새 글 */
	@RequestMapping(value = "list")
	public String TimelineList(HttpSession session, Model model) {
		model.addAttribute("memberDTO", (MemberDTO) session.getAttribute("login"));
		model.addAttribute("MenuVisible", true);
		model.addAttribute("PageTitle", "Timeline");
		return "timeline/timelineList";
	}

	@RequestMapping(value = "list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> TimelineList(@RequestParam(defaultValue = "1") int page, HttpSession session) throws Exception {
		MemberDTO loginDTO = (MemberDTO) session.getAttribute("login");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("timelineList", timelineService.timelineList(loginDTO.getMember_num(), (page - 1) * 7, 7));
		if (timelineService.timelineList(loginDTO.getMember_num(), page * 7, 1).size() > 0) {
			map.put("nextData", 1);
		}
		return map;
	}
}