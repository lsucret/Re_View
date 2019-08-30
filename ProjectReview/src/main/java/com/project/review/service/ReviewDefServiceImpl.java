package com.project.review.service;

import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.project.review.dao.ReviewDefDAO;
import com.project.review.dto.ReviewDTO;

@Service
public class ReviewDefServiceImpl implements ReviewDefService {

	@Inject
	ReviewDefDAO reviewDAO;

	@Override
	public int insertReview(ReviewDTO review) {
		return reviewDAO.insertReview(review);
	}

	@Override
	public Map<String, Object> selectReviewNum(int member_num, int review_num) {
		return reviewDAO.selectReviewNum(member_num, review_num);
	}

	@Override
	public int updateReview(int review_num, int member_num, int category_num, String review_title, String review_content, String title_color, int review_grade) {
		return reviewDAO.updateReview(review_num, member_num, category_num, review_title, review_content, title_color, review_grade);
	}

	@Override
	public int updateReviewContentImgUrl(String original_url, String update_url, int review_num) {
		return reviewDAO.updateReviewContentImgUrl(original_url, update_url, review_num);
	}

	@Override
	public int updateReviewTitleImgUrl(String title_img, int review_num) {
		return reviewDAO.updateReviewTitleImgUrl(title_img, review_num);
	}

	@Override
	public int deleteReview(int review_num, int member_num) {
		return reviewDAO.deleteReview(review_num, member_num);
	}

}