package com.project.review.dao;

import java.util.Map;

/**
 * 리뷰관리 - 리뷰 조회수 및 상세 페이지
 */

public interface ReviewDetailDAO {

	public int reviewHitsUpdate(String review_num); // 조회수 추가

	public Map<String, Object> selectReviewDetail(int review_num); // 리뷰 상세

	public Map<String, Object> selectReviewLike(int review_num, int member_num);

}