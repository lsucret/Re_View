package com.project.review.dao;

import java.util.HashMap;
import java.util.List;

import com.project.review.common.PagingDefault;

/**
 * 리뷰 리스트
 */

public interface ReviewListDAO {

	public List<HashMap<String, Object>> selectMapToggle(int bloggerNum); // Map

	public List<HashMap<String, Object>> selectListToggle(String num_url, PagingDefault pagingDefault); // List

	public int selectReviewListCnt(int bloggerNum, String categoryVal, String searchTxtField); // list total count

}