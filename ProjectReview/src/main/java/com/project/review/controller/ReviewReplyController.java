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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.review.dto.MemberDTO;
import com.project.review.dto.ReviewReplyDTO;
import com.project.review.service.ReviewReplyService;

/**
 * 리뷰댓글
 */

@Controller
@RequestMapping("/blog/review/reply/*")
public class ReviewReplyController {

	@Inject
	ReviewReplyService replyService;

	/* 댓글 불러오기 */
	@ResponseBody
	@GetMapping(value = "{rNum}")
	public List<ReviewReplyDTO> ReviewReplyList(@PathVariable("rNum") Integer review_num, @RequestParam(defaultValue = "1") int page) {
		return replyService.selectReviewReply(review_num, (page - 1) * 15);
	}

	/* 댓글 Count */
	@ResponseBody
	@RequestMapping(value = "count", method = RequestMethod.POST)
	public int ReviewReplyCnt(HttpServletRequest request) {
		int review_num = Integer.parseInt(request.getParameter("reviewNum") + "");
		return replyService.selectReivewReplyCount(review_num);
	}

	/* 댓글 쓰기 */
	@ResponseBody
	@PostMapping(value = "{rNum}")
	public ResponseEntity<String> ReviewReplyWrite(@PathVariable("rNum") Integer review_num, @RequestBody ReviewReplyDTO replyDTO, HttpServletRequest request,
			HttpSession session) {
		MemberDTO loginDTO = (MemberDTO) session.getAttribute("login"); // 세션에서 member_num빼서 넣음
		replyDTO.setMember_num(loginDTO.getMember_num());
		replyDTO.setReview_num(review_num); // 임시 글번호
		replyService.writeReply(replyDTO);
		return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
	}

	/* 댓글 수정 */
	@ResponseBody
	@RequestMapping(value = "{cNum}", method = { RequestMethod.PUT, RequestMethod.PATCH })
	public ResponseEntity<String> ReviewReplyModify(@PathVariable("cNum") Integer comment_num, @RequestBody ReviewReplyDTO replyDTO) throws Exception {
		replyDTO.setComment_num(comment_num);
		replyService.modifyReply(replyDTO);
		return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
	}

	/* 댓글 삭제 */
	@ResponseBody
	@RequestMapping(value = "{cNum}", method = RequestMethod.DELETE)
	public ResponseEntity<String> ReviewReplyDelete(@PathVariable("cNum") Integer comment_num) throws Exception {
		replyService.deleteReply(comment_num);
		return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
	}
}