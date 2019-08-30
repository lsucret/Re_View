package com.project.review.service;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.project.review.common.PagingDefault;
import com.project.review.dao.ReviewListDAO;

@Service
public class ReviewListServiceImpl implements ReviewListService {

	@Inject
	ReviewListDAO listDAO;

	@Override
	public List<HashMap<String, Object>> selectMapToggle(int bloggerNum) {
		return listDAO.selectMapToggle(bloggerNum);
	}

	@Override
	public List<HashMap<String, Object>> selectListToggle(String num_url, PagingDefault pagingDefault) {
		return listDAO.selectListToggle(num_url, pagingDefault);
	}

	@Override
	public int selectReviewListCnt(int bloggerNum, String categoryVal, String searchTxtField) {
		return listDAO.selectReviewListCnt(bloggerNum, categoryVal, searchTxtField);
	}
}