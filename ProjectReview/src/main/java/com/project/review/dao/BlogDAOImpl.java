package com.project.review.dao;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.project.review.dto.FollowStateDTO;

@Repository
public class BlogDAOImpl implements BlogDAO {

	@Inject
	SqlSession sqlSession;

	private static final String mapperName = "com.project.review.BlogMapper";

	@Override
	public FollowStateDTO selectFollowCnt(int bloggerNum) {
		return sqlSession.selectOne(mapperName + ".selectFollowCnt", bloggerNum);
	}

	@Override
	public void updateBlogMainURL(String blog_main, int mNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("blog_main", blog_main);
		map.put("member_num", mNum);
		sqlSession.update(mapperName + ".updateBlogMainURL", map);
	}

}