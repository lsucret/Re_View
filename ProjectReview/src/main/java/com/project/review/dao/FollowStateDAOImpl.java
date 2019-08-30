package com.project.review.dao;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
public class FollowStateDAOImpl implements FollowStateDAO {

	@Inject
	SqlSession sqlSession;

	private static final String mapperName = "com.project.review.FollowStateMapper";

	@Override
	public int viewFollowState(int member_num, int blogger_num) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("member_num", member_num);
		map.put("blogger_num", blogger_num);
		return sqlSession.selectOne(mapperName + ".viewFollowState", map);
	}

}