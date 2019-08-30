package com.project.review.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
public class TimeLineDAOImpl implements TimeLineDAO {

	@Inject
	SqlSession sqlSession;

	private static final String mapperName = "com.project.review.TimeLineMapper";

	@Override
	public List<HashMap<String, Object>> timelineList(int member_num, int start, int end) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("member_num", member_num);
		map.put("start", start);
		map.put("end", end);
		return sqlSession.selectList(mapperName + ".timelineList", map);
	}
}