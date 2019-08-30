package com.project.review.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.review.common.JsonUtil;
import com.project.review.dto.LoginDTO;
import com.project.review.dto.MemberDTO;
import com.project.review.service.AccountService;

/**
 * 계정관리 - 로그인, 로그아웃, 계정찾기
 */

@Controller
@RequestMapping("/account/*")
public class AccountController {

	@Inject
	AccountService accountService;
	@Inject
	BCryptPasswordEncoder passwordEncoder;

	/* 로그인 */
	@RequestMapping(value = "login") // url. "/account/" 매핑 상속받아서 "/account/login"의 경로를 가짐.
	public String Login(HttpServletRequest request, Model model) {
		String old_url = request.getHeader("referer"); // 이전 페이지
		if (old_url != null && (old_url.contains("null") || old_url.contains("login") || old_url.contains("logout") || old_url.contains("join"))) {
			old_url = null;
		}
		request.getSession().setAttribute("destination", old_url);
		model.addAttribute("PageTitle", "Login"); // login 페이지 상단에 출력될 문구 (${pageTitle})
		return "account/login";
	}

	@RequestMapping(value = "loginPost", method = RequestMethod.POST)
	public void Login(LoginDTO loginDTO, HttpServletRequest req, Model model) throws Exception {
		MemberDTO memberDTO = accountService.login(loginDTO);
		if (memberDTO == null) {
			return;
		}

		boolean passMatch = passwordEncoder.matches(loginDTO.getMember_pw(), memberDTO.getMember_pw());
		if (passMatch) {
			accountService.loginTime(memberDTO.getMember_id());
			HttpSession session = req.getSession();
			session.setAttribute("member", memberDTO);
			model.addAttribute("member", memberDTO);
		} else {
			return;
		}
	}

	/* 로그아웃 */
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String Logout(HttpServletRequest request) throws Exception {
		request.getSession().removeAttribute("login");
		return "account/logout";
	}

	/* 계정 찾기 */
	@RequestMapping(value = "find/{page}")
	public String userAccountSearch(@PathVariable String page, Model model) {
		String nowPageData = "";
		if (page.equals("id")) { // 아이디 찾기면 아이디찾기 화면
			nowPageData = "page_findId";
		} else if (page.equals("pw")) { // 패스워드 찾기면 패스워드찾기 화면
			nowPageData = "page_findPw";
		}

		model.addAttribute("PageTitle", "Find Account");
		model.addAttribute("nowPageData", nowPageData);
		return "account/find";
	}

	/* 회원탈퇴 */
	@RequestMapping(value = "leave")
	public String Leave(HttpServletRequest request, HttpSession session) {
		accountService.memberLeave((MemberDTO) session.getAttribute("login"));
		request.getSession().removeAttribute("login");
		return "redirect:/";
	}

	/* 계정찾기 - 계정 존재여부 체크 */
	@RequestMapping(value = "search", method = RequestMethod.POST)
	@ResponseBody
	public Object userAccountSearch(HttpServletRequest request) throws Exception {
		String member_id = request.getParameter("member_id") + "", member_name = request.getParameter("member_name") + "", member_phone = request.getParameter("member_phone") + "",
				nowPage = request.getParameter("nowPage");
		if (member_phone.trim().equals("") || member_id.trim().equals("") || member_name.trim().equals("")) {
			return null;
		}

		MemberDTO findDTO = accountService.userAccountSearch(member_id.trim(), member_name.trim(), member_phone.trim());
		if (findDTO == null) { // select 결과가 없으면
			return -1;
		}

		String result = "";
		if (nowPage.contains("id")) {
			result = findDTO.getMember_id();
			if (result.contains("@")) {
				int IDhidden = result.split("@")[1].length() - 3; // @ 뒤에 *처리 할 개수. @뒤 3자리까지 보여줌
				String resultID = "";
				for (int i = 0; i < IDhidden; i++) {
					resultID += "*";
				}

				IDhidden = result.length() - IDhidden;
				resultID = result.substring(0, IDhidden) + resultID;
				result = resultID;
			}
		}

		return JsonUtil.OneStringToJson(result);
	}

	/* 새 비밀번호 등록 */
	@RequestMapping(value = "newpw", method = RequestMethod.POST)
	@ResponseBody
	public Object userAccountNewPassword(HttpServletRequest request) throws Exception {
		String member_pw = request.getParameter("member_pw") + "", member_pw2 = request.getParameter("member_pw2") + "";
		String member_id = request.getParameter("member_id") + "", member_phone = request.getParameter("member_phone") + "";

		if (member_pw.trim().equals("") || member_pw2.trim().equals("")) { // 입력안된 값이 있을 경우
			return null;
		} else if (!member_pw.trim().equals(member_pw2.trim())) { // 비밀번호, 비밀번호 확인 이 불일치 할 경우
			return 0;
		} else {
			String encodePass = passwordEncoder.encode(member_pw);
			return accountService.userAccountNewPw(member_id, member_phone, encodePass);
		}
	}
}