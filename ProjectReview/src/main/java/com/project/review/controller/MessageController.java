package com.project.review.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.review.dto.MemberDTO;
import com.project.review.dto.MessageDTO;
import com.project.review.service.MessageService;

/**
 * 메세지관리
 */

@Controller
@RequestMapping("/message/*")
public class MessageController {

	@Inject
	MessageService messageService;

	@ResponseBody
	@PostMapping(value = "send")
	public ResponseEntity<String> sendMessage(@RequestBody MessageDTO messageDTO, HttpSession session) throws Exception {
		MemberDTO loginDTO = (MemberDTO) session.getAttribute("login");
		messageDTO.setMessage_sender(loginDTO.getMember_num());
		if (messageDTO.getMessage_title() == null || messageDTO.getMessage_title().trim().equals("")) {
			messageDTO.setMessage_title("(제목없음)");
		}
		messageService.sendMessage(messageDTO);
		return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "list")
	public List<MessageDTO> messageList(HttpServletRequest request, HttpSession session) throws Exception {
		MemberDTO loginDTO = (MemberDTO) session.getAttribute("login");
		return messageService.messageList(loginDTO.getMember_num(), request.getParameter("message_read"), request.getParameter("tab"));
	}

	@ResponseBody
	@RequestMapping(value = "detail", method = RequestMethod.POST)
	public MessageDTO messageDetail(HttpServletRequest request, HttpSession session) {
		MemberDTO loginDTO = (MemberDTO) session.getAttribute("login");
		int mnum = Integer.parseInt(request.getParameter("message_num"));
		String tab = request.getParameter("page_tab");
		if (tab.equals("receive")) {
			messageService.updateMessageReadCheck(loginDTO.getMember_num(), mnum);
		}
		return messageService.messageDetail(loginDTO.getMember_num(), mnum, tab);
	}

	@ResponseBody
	@PutMapping(value = "delete/{delchk}/{messageNum}")
	public ResponseEntity<String> deleteMessage(@PathVariable int delchk, @PathVariable int messageNum) {
		messageService.deleteMessage(messageNum, delchk); // delchk : 0은 받은메세지, 1는 보낸메세지
		return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "new/count", method = RequestMethod.POST)
	public int newMessageCount(HttpSession session) {
		MemberDTO loginDTO = (MemberDTO) session.getAttribute("login");
		return messageService.newMessageTotalCount(loginDTO.getMember_num());
	}
}