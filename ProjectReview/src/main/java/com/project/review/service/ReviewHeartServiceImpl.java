package com.project.review.service;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.project.review.common.PagingDefault;
import com.project.review.dao.ReviewHeartDAO;

@Service
public class ReviewHeartServiceImpl implements ReviewHeartService {

	@Inject
	ReviewHeartDAO heartDAO;

	@Override
	public List<HashMap<String, Object>> selectReviewHeart(String num_url, PagingDefault pagingDefault) {
		return heartDAO.selectReviewHeart(num_url, pagingDefault);
	}

	@Override
	public int selectReviewHeartTotalCount(String num_url, String category, String searchTxtField) {
		return heartDAO.selectReviewHeartTotalCount(num_url, category, searchTxtField);
	}

	@Override
	public int ReviewHeartManage(int member_num, int review_num, boolean state) {
		return heartDAO.ReviewHeartManage(member_num, review_num, state);
	}

	@Override
	public int CountReviewHeart(int review_num) {
		return heartDAO.CountReviewHeart(review_num);
	}

}