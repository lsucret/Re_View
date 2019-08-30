package com.project.review.controller;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.project.review.common.JsonUtil;
import com.project.review.dto.MemberDTO;
import com.project.review.service.MemberDefService;
import com.project.review.service.ReviewDetailService;

/**
 * 리뷰관리 - 조회수, 상세
 */

@Controller
@RequestMapping("/blog/review/detail/")
public class ReviewDetailController {

	@Inject
	MemberDefService defService;
	@Inject
	ReviewDetailService detailService;

	/* 조회수 증가 */
	@RequestMapping(value = "hitCount")
	@ResponseBody
	public String reviewHitCount(HttpServletRequest req, HttpSession session) {
		MemberDTO loginDTO = (MemberDTO) session.getAttribute("login"); // 로긴한 user_num
		String writer_num_url = req.getParameter("num_url"), review_num = req.getParameter("review_num");
		if (loginDTO == null || !loginDTO.getNum_url().equals(writer_num_url)) { // 비로그인, 다른블로거 글만 카운트 UP
			detailService.reviewHitsUpdate(review_num);
		}
		return JsonUtil.OneStringToJson("/blog/review/detail/" + writer_num_url + "/" + review_num);
	}

	/* 리뷰 상세 */
	@RequestMapping(value = "{ViewUserURL}/{pageNum}")
	public ModelAndView ReviewDetail(@PathVariable String ViewUserURL, @PathVariable String pageNum, HttpSession session) throws Exception {
		ModelAndView mv = new ModelAndView("review/reviewDetail");
		int writerNum = defService.selectMemerNumDecrypt(ViewUserURL); // 게시글 등록자
		mv.addObject("memberDTO", defService.selectMemberNum(writerNum));
		mv.addObject("review", detailService.selectReviewDetail(Integer.parseInt(pageNum)));
		mv.addObject("PageTitle", "Review");
		return mv;
	}

	/* 상세페이지 좋아요 여부 */
	@RequestMapping(value = "like", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> ReviewDetailLikeState(HttpSession session, HttpServletRequest request) {
		MemberDTO loginDTO = (MemberDTO) session.getAttribute("login");
		int member_num = 0;
		if (loginDTO != null) {
			member_num = loginDTO.getMember_num();
		}
		Map<String, Object> map = detailService.selectReviewLike(Integer.parseInt(request.getParameter("review_num")), member_num);
		if (map == null) {
			map = new HashMap<String, Object>();
			map.put("state", 0);
			map.put("total", 0);
		}
		return map;
	}
}