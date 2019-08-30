package com.project.review.controller;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.project.review.common.Paging;
import com.project.review.common.PagingDefault;
import com.project.review.dto.GuestDTO;
import com.project.review.dto.MemberDTO;
import com.project.review.service.FollowStateService;
import com.project.review.service.GuestService;
import com.project.review.service.MemberDefService;

/**
 * 방명록 관리
 */

@Controller
@RequestMapping("/blog/guest/*")
public class GuestController {

	@Inject
	MemberDefService defService;
	@Inject
	FollowStateService stateService;
	@Inject
	GuestService guestService;

	private Paging paging;

	@RequestMapping(value = "{ViewUserURL}")
	public ModelAndView GuestMain(@PathVariable String ViewUserURL, @ModelAttribute("pagingDefault") PagingDefault pagingDefault, HttpSession session) {
		int writerNum = 0, hiddenCheck = 1;
		boolean menuVisible = false; // 메뉴 보여주는 조건. false는 이웃블로그, true 내 블로그

		MemberDTO loginDTO = (MemberDTO) session.getAttribute("login");
		if (loginDTO != null) {
			writerNum = loginDTO.getMember_num();
			if (loginDTO.getNum_url().equals(ViewUserURL)) { // 내블로그 일 때
				hiddenCheck = 0;
				menuVisible = true;
			}
		} else {
			hiddenCheck = 2;
		}

		int bloggerNum = defService.selectMemerNumDecrypt(ViewUserURL);
		paging = new Paging();
		paging.setPagingDefault(pagingDefault);
		paging.setTotalCount(guestService.selectGuestTotalCount(bloggerNum, writerNum, hiddenCheck, pagingDefault));

		ModelAndView mav = new ModelAndView("guest/guestList");
		mav.addObject("memberDTO", defService.selectMemberNum(bloggerNum));
		mav.addObject("guestList", guestService.selectGuestList(bloggerNum, writerNum, hiddenCheck, pagingDefault));
		mav.addObject("MenuVisible", menuVisible);
		mav.addObject("paging", paging);
		mav.addObject("pagingDefault", pagingDefault);
		mav.addObject("PageTitle", "Guest");
		return mav;
	}

	/* 방명록 등록 */
	@RequestMapping(value = "write", method = RequestMethod.POST)
	@ResponseBody
	public int GuestWrite(HttpServletRequest request, HttpSession session) {
		String content = request.getParameter("content") + "";
		if (content.trim().equals("")) {
			return 0;
		}

		String strBloggerURL[] = request.getParameter("viewURL").toString().split("/");
		String splitBloggerURL = strBloggerURL[strBloggerURL.length - 1];
		if (splitBloggerURL.contains("?")) { // 검색한 상태일 때
			splitBloggerURL = splitBloggerURL.split("\\?")[0];
		}
		MemberDTO memberDTO = (MemberDTO) session.getAttribute("login");
		return guestService.insertGuest(splitBloggerURL, memberDTO.getMember_num(), content, Boolean.parseBoolean(request.getParameter("hidden_check")));
	}

	/* 방명록 수정 내용 출력 */
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	@ResponseBody
	public GuestDTO GuestEditContent(HttpServletRequest request) {
		return guestService.selectGuestNum(Integer.parseInt(request.getParameter("guest_num") + ""));
	}

	/* 방명록 등록 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> GuestUpdate(HttpServletRequest request, HttpSession session) {
		String content = request.getParameter("content") + "";
		if (content.trim().equals("")) {
			return null;
		}

		int hidden_check = 0;
		int intGuestNum = Integer.parseInt(request.getParameter("guest_num") + "");
		boolean hidden = Boolean.parseBoolean(request.getParameter("hidden_check"));
		if (hidden) {
			hidden_check = 1;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", guestService.updateGuest(intGuestNum, content, hidden_check));
		return map;
	}

	/* 방명록 삭제 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> GuestDelete(HttpServletRequest request, HttpSession session) {
		int intGuestNum = Integer.parseInt(request.getParameter("guest_num") + "");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", guestService.deleteGuest(intGuestNum));
		return map;
	}
}