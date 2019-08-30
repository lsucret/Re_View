package com.project.review.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.project.review.dao.ReviewReplyDAO;
import com.project.review.dto.ReviewReplyDTO;

@Service
public class ReviewReplyServiceImpl implements ReviewReplyService {

	@Inject
	ReviewReplyDAO replyDAO;

	@Override
	public List<ReviewReplyDTO> selectReviewReply(int review_num, int start) {
		return replyDAO.selectReviewReply(review_num, start);
	}

	@Override
	public int selectReivewReplyCount(int review_num) {
		return replyDAO.selectReivewReplyCount(review_num);
	}

	@Override
	public void writeReply(ReviewReplyDTO replyDTO) {
		replyDAO.writeReply(replyDTO);
	}

	@Override
	public void modifyReply(ReviewReplyDTO replyDTO) throws Exception {
		replyDAO.modifyReply(replyDTO);
		System.out.println(replyDTO.getComment_content());
	}

	@Override
	public void deleteReply(Integer comment_num) throws Exception {
		replyDAO.deleteReply(comment_num);
	}

}