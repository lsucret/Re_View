package com.project.review.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.project.review.common.PagingDefault;

@Repository
public class ReviewHeartDAOImpl implements ReviewHeartDAO {

	@Inject
	SqlSession sqlSession;

	private static final String mapperName = "com.project.review.ReviewHeartMapper";

	@Override
	public List<HashMap<String, Object>> selectReviewHeart(String num_url, PagingDefault pagingDefault) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("num_url", num_url);
		map.put("searchTxtField", pagingDefault.getSearchTxtField());
		map.put("start", pagingDefault.getPageStart());
		map.put("sort", pagingDefault.getSort());
		map.put("category", pagingDefault.getCategory());
		return sqlSession.selectList(mapperName + ".selectReviewHeart", map);
	}

	@Override
	public int selectReviewHeartTotalCount(String num_url, String category, String searchTxtField) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("num_url", num_url);
		map.put("searchTxtField", searchTxtField);
		map.put("category", category);
		return sqlSession.selectOne(mapperName + ".selectReviewHeartTotalCount", map);
	}

	@Override
	public int ReviewHeartManage(int member_num, int review_num, boolean state) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("member_num", member_num);
		map.put("review_num", review_num);
		if (state) {
			return sqlSession.insert(mapperName + ".ReviewHeartAdd", map);
		} else {
			return sqlSession.delete(mapperName + ".ReviewHeartCancel", map);
		}
	}

	@Override
	public int CountReviewHeart(int review_num) {
		return sqlSession.selectOne(mapperName + ".CountReviewHeart", review_num);
	}

}