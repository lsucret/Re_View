package com.project.review.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oreilly.servlet.MultipartRequest;
import com.project.review.common.CustomFileRenamePolicy;
import com.project.review.dto.MemberDTO;
import com.project.review.service.MemberDefService;
import com.project.review.service.MemberService;

/**
 * 회원관리 - 회원가입, 회원정보수정
 */

@Controller
@RequestMapping("/member/*")
public class MemberController {

	@Inject
	MemberService memberService;
	@Inject
	MemberDefService defService;
	@Inject
	BCryptPasswordEncoder passwordEncoder;

	/* 회원가입 */
	@RequestMapping(value = "join")
	public String MemberJoin(Model model) {
		model.addAttribute("PageTitle", "Join");
		return "member/memberJoin";
	}

	@RequestMapping(value = "join", method = RequestMethod.POST)
	public String MemberJoin(MemberDTO memberDTO) {
		memberDTO.setMember_pw(passwordEncoder.encode(memberDTO.getMember_pw())); // 비밀번호 암호화
		int mNum = memberService.joinMember(memberDTO);
		memberService.updateMemberNumDecrypt(mNum); // 회원번호 암호화
		return "redirect:/";
	}

	/* 회원정보 수정 */
	@RequestMapping(value = "modify")
	public String MemberModify(Model model, HttpSession session) throws Exception {
		model.addAttribute("memberDTO", (MemberDTO) session.getAttribute("login"));
		model.addAttribute("MenuVisible", true);
		model.addAttribute("PageTitle", "My Info");
		return "member/memberModify";
	}

	@RequestMapping(value = "modify", method = RequestMethod.POST)
	@ResponseBody
	public int modifySubmit(HttpServletRequest request, HttpSession session) throws Exception {
		MemberDTO originalDTO = (MemberDTO) session.getAttribute("login");
		String updateName = null, updateNick = null, updatePhone = null, updatePass = null;
		if (request.getParameter("section").equals("info")) { // 회원정보 수정할 때
			String name = request.getParameter("member_name"), nick = request.getParameter("member_nickname"), phone = request.getParameter("member_phone");
			if ((originalDTO.getMember_name().equals(name) && originalDTO.getMember_nickname().equals(nick) && originalDTO.getMember_phone().equals(phone))) {
				return -1;
			}
			updateName = name;
			updateNick = nick;
			updatePhone = phone;
		} else {
			String oldPass = request.getParameter("old_pw"), newPass = request.getParameter("new_pw");
			if (!passwordEncoder.matches(oldPass, originalDTO.getMember_pw())) { // 기존 비밀번호 입력 불일치
				return -2;
			}
			if (passwordEncoder.matches(newPass, originalDTO.getMember_pw())) { // 기존 비밀번호와 새 비밀번호가 일치하면
				return -1;
			}

			updatePass = passwordEncoder.encode(newPass); // 새로 입력한 비밀번호 암호화
		}
		int result = memberService.updateMember(updateName, updateNick, updatePhone, updatePass, null, originalDTO.getMember_num()); // 최종적으로 수정하는 부분이기에 맨 밑으로 와야한다.

		// 변경된 정보를 세션에 새로 저장. 그리고 uri에 회원가입일이 필요하여 DB에서 다시 가져왔습니다.
		request.getSession().removeAttribute("login");
		request.getSession().setAttribute("login", defService.selectMemberNum(originalDTO.getMember_num()));
		return result;
	}

	/* 아이디 중복 체크 */
	@ResponseBody
	@RequestMapping(value = "idDuplicateCheck", method = RequestMethod.POST)
	public boolean idDuplicateCheck(HttpServletRequest request) {
		boolean result = true;
		if (memberService.idDuplicateCheck(request.getParameter("member_id")) != null) { // 등록된 아이디
			result = false;
		}
		return result;
	}

	/* 휴대폰 중복체크 */
	@RequestMapping(value = "phoneDuplicateCheck", method = RequestMethod.POST)
	@ResponseBody
	public boolean phoneDuplicateCheck(HttpServletRequest request, HttpSession session) {
		MemberDTO memberDTO = (MemberDTO) session.getAttribute("login");
		String inputPhone = request.getParameter("member_phone"), phone = "";
		boolean result = true;
		if (memberDTO != null) {
			phone = memberDTO.getMember_phone();
		}
		if (!phone.equals(inputPhone) && memberService.phoneDuplicateCheck(inputPhone) != null) { // 등록된 휴대폰 번호
			result = false;
		}
		return result;
	}

	/* 프로필 사진 업로드 */
	@RequestMapping(value = "profileUpload")
	public String fileUpload(HttpServletRequest req, HttpSession session) throws Exception {
		MemberDTO sessionInfo = (MemberDTO) session.getAttribute("login");
		int sizeLimit = 1024 * 1024 * 3;
		String path = "D:\\reviewImg\\profile";
		String fileName = "", timeFormat = "", sendPage = "";

		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss"); // Java 시간을 파일명 작성에도 사용하고 DB에도 보냅니다.
		Date time = new Date();
		timeFormat = format.format(time);

		int member_num = sessionInfo.getMember_num();

		MultipartRequest multi = new MultipartRequest(req, path, sizeLimit, "UTF-8", new CustomFileRenamePolicy(member_num, timeFormat));
		fileName = multi.getFilesystemName("profileImg");
		memberService.updateMember(null, null, null, null, "/reviewImg/profile/" + fileName, member_num);

		File file = new File("D:" + sessionInfo.getMember_profile()); // 저장소에서 이전 프로필 사진을 삭제
		if (file.exists()) {
			file.delete();
		}

		session.removeAttribute("login");
		session.setAttribute("login", defService.selectMemberNum(member_num)); // 바뀐 member_profile 반영하여 세션값을 변경
		sendPage = multi.getParameter("sendNowPage");
		return "redirect:" + sendPage;
	}

	@RequestMapping(value = "profileDelete")
	@ResponseBody
	public int profileImgDefault(HttpSession session) throws Exception {
		MemberDTO loginDTO = (MemberDTO) session.getAttribute("login");
		File file = new File("D:" + loginDTO.getMember_profile()); // 저장소에서 이전 프로필 사진을 삭제
		if (file.exists()) {
			file.delete();
		}

		memberService.updateMember(null, null, null, null, "/img/default-profile.png", loginDTO.getMember_num());
		session.removeAttribute("login");
		session.setAttribute("login", defService.selectMemberNum(loginDTO.getMember_num()));
		return 1;
	}
}