package com.project.review.controller;

import java.util.HashMap;
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

import com.project.review.dto.FollowStateDTO;
import com.project.review.dto.MemberDTO;
import com.project.review.service.BlogService;
import com.project.review.service.MemberDefService;

/**
 * 블로그 관리 - 메인 URL(리스트 또는 맵 redirect), 설정
 */

@Controller
@RequestMapping("/blog/*")
public class BlogController {

	@Inject
	MemberDefService defService;
	@Inject
	BlogService blogService;

	@RequestMapping(value = "{ViewUserURL}")
	public String BlogMain(@PathVariable String ViewUserURL) {
		String bloggerMain = defService.selectBlogURL(ViewUserURL);
		return "redirect:/blog/" + bloggerMain + "/" + ViewUserURL; // 메인 들어오면 블로거가 main으로 설정해둔 화면으로 전환
	}

	/* 블로그 사이드 메뉴에 이웃 수 출력 */
	@RequestMapping(value = "side/count", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> FollowCnt(HttpServletRequest request) throws Exception {
		int bloggerNum = defService.selectMemerNumDecrypt(request.getParameter("check_num").toString());
		FollowStateDTO state = blogService.selectFollowCnt(bloggerNum);
		Map<String, Object> map = new HashMap<String, Object>();
		int cnt1 = 0, cnt2 = 0;
		if (state != null) {
			cnt1 = state.getCfollow();
			cnt2 = state.getCfollower();
		}
		map.put("cnt1", cnt1);
		map.put("cnt2", cnt2);
		return map;
	}

	/* 설정 */
	@RequestMapping(value = "setup")
	public String BlogSetUp(HttpSession session, Model model) {
		MemberDTO loginDTO = (MemberDTO) session.getAttribute("login");
		model.addAttribute("memberDTO", defService.selectMemberNum(loginDTO.getMember_num())); // update 후 세션값때문에 값 변경이 되지 않는 부분이 있어서 현재 데이터 다시 호출
		model.addAttribute("MenuVisible", true);
		model.addAttribute("PageTitle", "Set-up");
		return "blog/blogSetup";
	}

	@RequestMapping(value = "setup/update", method = RequestMethod.POST)
	@ResponseBody
	public void BlogSetUp(HttpServletRequest request, HttpSession session) throws Exception {
		MemberDTO loginDTO = (MemberDTO) session.getAttribute("login");
		String updateData = request.getParameter("updateData").toString();
		if (updateData.contains("img1")) {
			updateData = "list";
		} else if (updateData.contains("img2")) {
			updateData = "map";
		}

		blogService.updateBlogMainURL(updateData, loginDTO.getMember_num());
	}
}