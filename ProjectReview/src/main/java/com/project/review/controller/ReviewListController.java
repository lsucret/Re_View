package com.project.review.controller;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.review.common.JsonUtil;
import com.project.review.common.Paging;
import com.project.review.common.PagingDefault;
import com.project.review.dto.MemberDTO;
import com.project.review.service.FollowStateService;
import com.project.review.service.MemberDefService;
import com.project.review.service.ReviewListService;

/**
 * 리뷰 리스트 관리 - 리스트 출력(map, list)
 */

@Controller
@RequestMapping("/blog/*")
public class ReviewListController {

	@Inject
	MemberDefService defService;
	@Inject
	ReviewListService listService;
	@Inject
	FollowStateService stateService;

	private Paging paging;
	private PagingDefault pagingDefault;

	/* 블로그 메인 map */
	@RequestMapping(value = "map/{ViewUserURL}")
	public String BlogMainMap(@PathVariable String ViewUserURL, HttpSession session, Model model) {
		boolean menuVisible = false; // 메뉴 보여주는 조건. false는 이웃블로그, true 내 블로그

		MemberDTO loginDTO = (MemberDTO) session.getAttribute("login");
		if (loginDTO != null && loginDTO.getNum_url().equals(ViewUserURL)) { // 내블로그 일 때
			menuVisible = true;
		}

		int bloggerNum = defService.selectMemerNumDecrypt(ViewUserURL); // 회원번호 복호화
		List<HashMap<String, Object>> mapToggle = listService.selectMapToggle(bloggerNum);
		String mapToggleJsonObj = JsonUtil.ListToJson(mapToggle);

		model.addAttribute("memberDTO", defService.selectMemberNum(bloggerNum));
		model.addAttribute("MenuVisible", menuVisible);
		model.addAttribute("mapToggle", mapToggleJsonObj); // 추가
		model.addAttribute("PageTitle", "Blog");
		return "blog/mainMap";
	}

	/* 블로그 메인 list */
	@RequestMapping(value = "list/{ViewUserURL}")
	public String BlogMainList(@PathVariable String ViewUserURL, @ModelAttribute("pagingDefault") PagingDefault pagingDefault, HttpServletRequest request, HttpSession session,
			Model model) {
		int bloggerNum = defService.selectMemerNumDecrypt(ViewUserURL);
		boolean menuVisible = false; // 메뉴 보여주는 조건. false는 이웃블로그, true 내 블로그
		MemberDTO loginDTO = (MemberDTO) session.getAttribute("login");
		if (loginDTO != null && loginDTO.getMember_num() == bloggerNum) { // 로그인 상태
			menuVisible = true;
		}

		/* 페이징 처리 */
		pagingDefault.setColumn(6);
		paging = new Paging();
		paging.setPagingDefault(pagingDefault);
		paging.setTotalCount(listService.selectReviewListCnt(bloggerNum, pagingDefault.getCategory(), pagingDefault.getSearchTxtField()));
		this.pagingDefault = pagingDefault;

		model.addAttribute("memberDTO", defService.selectMemberNum(bloggerNum));
		model.addAttribute("MenuVisible", menuVisible);
		model.addAttribute("paging", paging);
		model.addAttribute("pagingDefault", pagingDefault);
		model.addAttribute("PageTitle", "Blog");
		return "blog/mainList";
	}

	@RequestMapping(value = "list/read", method = RequestMethod.GET, produces = "text/json; charset=UTF-8")
	@ResponseBody
	public String BlogMainList2(HttpServletRequest request) {
		pagingDefault.setSearchTxtField(request.getParameter("searchTxtField"));
		pagingDefault.setCategory(request.getParameter("category"));
		pagingDefault.setSort(request.getParameter("sort"));
		List<HashMap<String, Object>> listToggle = listService.selectListToggle(request.getParameter("bloggerNum"), pagingDefault);
		return JsonUtil.ListToJson(listToggle);
	}
}