package com.project.review.service;

import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.project.review.dao.ReviewDetailDAO;

@Service
public class ReviewDetailServiceImpl implements ReviewDetailService {

	@Inject
	ReviewDetailDAO detailDAO;

	@Override
	public int reviewHitsUpdate(String review_num) {
		return detailDAO.reviewHitsUpdate(review_num);
	}

	@Override
	public Map<String, Object> selectReviewDetail(int review_num) {
		return detailDAO.selectReviewDetail(review_num);
	}

	@Override
	public Map<String, Object> selectReviewLike(int review_num, int member_num) {
		return detailDAO.selectReviewLike(review_num, member_num);
	}

}