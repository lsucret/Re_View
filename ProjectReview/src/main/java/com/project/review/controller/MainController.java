package com.project.review.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.review.common.JsonUtil;
import com.project.review.dto.MemberDTO;
import com.project.review.service.MainService;

/**
 * 메인 - 메인 MAP, 지난주 베스트 블로거, 블로거 검색, 타임라인
 */

@Controller
public class MainController {

	@Inject
	MainService mainService;

	@RequestMapping(value = "")
	public String Main(Model model) {
		model.addAttribute("top4ReviewList", mainService.selectTop4ReviewList());
		model.addAttribute("bestUserList", mainService.lastMonthBestUser());
		return "main";
	}

	@RequestMapping(value = "/main/map/list", method = RequestMethod.GET, produces = "text/json; charset=UTF-8")
	@ResponseBody
	public String MainReviewList(HttpServletRequest request) {
		List<HashMap<String, Object>> reviewList = mainService.selectMainMap(request.getParameter("lat"), request.getParameter("lng"));
		return JsonUtil.ListToJson(reviewList);
	}

	@RequestMapping(value = "/main/map/info", method = RequestMethod.POST, produces = "text/json; charset=UTF-8") // 장소 클릭 시
	@ResponseBody
	public String MainReviewInfo(HttpServletRequest request) throws Exception {
		List<HashMap<String, Object>> reviewList = mainService.selectMainMapReviewInfo(request.getParameter("lat"), request.getParameter("lng"));
		return JsonUtil.ListToJson(reviewList);
	}

	/* 장소 검색시 마커 출력 */
	@RequestMapping(value = "/main/map/search", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String MainReviewSerach(@RequestBody String searchKeyword, HttpSession session) throws Exception {
		MemberDTO loginDTO = (MemberDTO) session.getAttribute("login");
		int member_num = (loginDTO != null) ? loginDTO.getMember_num() : 0;

		String keyword = JsonUtil.JsonToMap(searchKeyword).get("keyword").toString();
		String keywordArr[] = keyword.substring(1, keyword.length() - 1).split(", ");

		List<HashMap<String, Object>> reviewList = mainService.selectMainMapReviewSearch(keywordArr, member_num);
		return JsonUtil.ListToJson(reviewList);
	}

	/* 블로거 검색 */
	@RequestMapping(value = "/search/user", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> searchUser(HttpServletRequest request, HttpSession session) throws Exception {
		MemberDTO loginDTO = (MemberDTO) session.getAttribute("login");
		int member_num = 0;
		if (loginDTO != null) {
			member_num = loginDTO.getMember_num();
		}
		List<HashMap<String, Object>> userList = mainService.selectUserList(member_num, request.getParameter("searchUser"));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userList", userList);
		map.put("loginNum", member_num);
		return map;
	}

}