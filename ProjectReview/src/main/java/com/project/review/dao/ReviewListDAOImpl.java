package com.project.review.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.project.review.common.PagingDefault;

@Repository
public class ReviewListDAOImpl implements ReviewListDAO {

	@Inject
	SqlSession sqlSession;

	private static final String mapperName = "com.project.review.ReviewListMapper";

	@Override
	public List<HashMap<String, Object>> selectMapToggle(int bloggerNum) {
		return sqlSession.selectList(mapperName + ".selectMapToggle", bloggerNum);
	}

	@Override
	public List<HashMap<String, Object>> selectListToggle(String num_url, PagingDefault pagingDefault) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("num_url", num_url);
		map.put("category", pagingDefault.getCategory());
		map.put("searchTxtField", pagingDefault.getSearchTxtField());
		map.put("start", pagingDefault.getPageStart());
		map.put("end", pagingDefault.getColumn());
		map.put("sort", pagingDefault.getSort());
		return sqlSession.selectList(mapperName + ".selectListToggle", map);
	}

	@Override
	public int selectReviewListCnt(int bloggerNum, String categoryVal, String searchTxtField) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("blogger_num", bloggerNum);
		map.put("category", categoryVal);
		map.put("searchTxtField", searchTxtField);
		return sqlSession.selectOne(mapperName + ".selectReviewListCnt", map);
	}
}