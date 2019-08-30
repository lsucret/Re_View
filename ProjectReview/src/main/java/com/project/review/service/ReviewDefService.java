package com.project.review.service;

import java.util.Map;

import com.project.review.dto.ReviewDTO;

/**
 * 리뷰관리 - 등록, 수정, 삭제
 */

public interface ReviewDefService {

	public int insertReview(ReviewDTO review); // 리뷰 등록

	public Map<String, Object> selectReviewNum(int member_num, int review_num); // 리뷰 수정시 보여줄 데이터

	public int updateReview(int review_num, int member_num, int category_num, String review_title, String review_content, String title_color, int review_grade); // 리뷰 수정

	public int updateReviewContentImgUrl(String original_url, String update_url, int review_num); // 이미지 경로 변경

	public int updateReviewTitleImgUrl(String title_img, int review_num); // 이미지 타이틀 경로 변경

	public int deleteReview(int review_num, int member_num); // 리뷰 삭제

}