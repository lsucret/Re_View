package com.project.review.dao;

import java.util.HashMap;
import java.util.List;

import com.project.review.common.PagingDefault;

/**
 * 리뷰 좋아요
 */

public interface ReviewHeartDAO {

	public List<HashMap<String, Object>> selectReviewHeart(String num_url, PagingDefault pagingDefault);

	public int selectReviewHeartTotalCount(String num_url, String category, String searchTxtField);

	public int ReviewHeartManage(int member_num, int review_num, boolean state); // 좋아요 추가 or 취소

	public int CountReviewHeart(int review_num); // 리뷰 좋아요 수

}