package com.project.review.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.review.common.JsonUtil;
import com.project.review.common.Paging;
import com.project.review.common.PagingDefault;
import com.project.review.dto.MemberDTO;
import com.project.review.service.ReviewHeartService;

/**
 * 리뷰좋아요 관리 - 내가 좋아요한 리뷰 출력, 좋아요 등록 및 취소
 */

@Controller
@RequestMapping("/blog/review/heart/*")
public class ReviewHeartController {

	@Inject
	ReviewHeartService heartService;

	private Paging paging;
	private PagingDefault pagingDefault;

	@RequestMapping(value = "list")
	public String ReviewWrite(@ModelAttribute("pagingDefault") PagingDefault pagingDefault, HttpSession session, Model model) {
		MemberDTO loginDTO = (MemberDTO) session.getAttribute("login");

		pagingDefault.setColumn(12);
		paging = new Paging();
		paging.setPagingDefault(pagingDefault);
		paging.setTotalCount(heartService.selectReviewHeartTotalCount(loginDTO.getNum_url(), pagingDefault.getCategory(), pagingDefault.getSearchTxtField()));
		this.pagingDefault = pagingDefault;

		model.addAttribute("memberDTO", loginDTO);
		model.addAttribute("MenuVisible", true);
		model.addAttribute("paging", paging);
		model.addAttribute("pagingDefault", pagingDefault);
		model.addAttribute("PageTitle", "Like");
		return "heart/heartList";
	}

	@RequestMapping(value = "list/read", method = RequestMethod.GET, produces = "text/json; charset=UTF-8")
	@ResponseBody
	public String ReviewWrite2(HttpServletRequest request) {
		pagingDefault.setSearchTxtField(request.getParameter("searchTxtField"));
		pagingDefault.setCategory(request.getParameter("category"));
		pagingDefault.setSort(request.getParameter("sort"));
		List<HashMap<String, Object>> heartList = heartService.selectReviewHeart(request.getParameter("bloggerNum"), pagingDefault);
		return JsonUtil.ListToJson(heartList);
	}

	/* 좋아요 등록 및 취소 */
	@ResponseBody
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public Map<String, Object> ReviewLike(HttpServletRequest req, HttpSession session) throws Exception {
		MemberDTO loginDTO = (MemberDTO) session.getAttribute("login");
		int reviewNum = Integer.parseInt(req.getParameter("reviewNum")); // int형으로 변환
		String pageURL = req.getParameter("pageURL") + "", returnClass = "fas fa-heart"; // 디폴트:등록
		boolean addState = Boolean.parseBoolean(req.getParameter("addState"));
		if (!addState) { // 좋아요 취소
			returnClass = "far fa-heart";
		}
		heartService.ReviewHeartManage(loginDTO.getMember_num(), reviewNum, addState); // 좋아요 추가 or 취소

		Map<String, Object> map = new HashMap<String, Object>();
		if (pageURL.contains("review/detail")) { // 리뷰상세 페이지에서만 좋아요 수 return
			map.put("likeCnt", heartService.CountReviewHeart(reviewNum));
		}
		map.put("viewClass", returnClass);
		return map;
	}
}