package com.project.review.dao;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
public class ReviewDetailDAOImpl implements ReviewDetailDAO {

	@Inject
	SqlSession sqlSession;

	private static final String mapperName = "com.project.review.ReviewDetailMapper";

	@Override
	public int reviewHitsUpdate(String review_num) {
		return sqlSession.update(mapperName + ".reviewHitsUpdate", review_num);
	}

	@Override
	public Map<String, Object> selectReviewDetail(int review_num) {
		return sqlSession.selectOne(mapperName + ".selectReviewDetail", review_num);
	}

	@Override
	public Map<String, Object> selectReviewLike(int review_num, int member_num) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("review_num", review_num);
		map.put("member_num", member_num);
		return sqlSession.selectOne(mapperName + ".selectReviewLike", map);
	}

}