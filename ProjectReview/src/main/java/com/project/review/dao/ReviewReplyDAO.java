package com.project.review.dao;

import java.util.List;

import com.project.review.dto.ReviewReplyDTO;

/**
 * 리뷰 댓글
 */

public interface ReviewReplyDAO {

	public List<ReviewReplyDTO> selectReviewReply(int review_num, int start); // 댓글 불러오기

	public int selectReivewReplyCount(int review_num); // 댓글 count

	public void writeReply(ReviewReplyDTO replyDTO); // 댓글 쓰기

	public void modifyReply(ReviewReplyDTO replyDTO) throws Exception; // 댓글 수정

	public void deleteReply(Integer comment_num) throws Exception; // 댓글 삭제

}