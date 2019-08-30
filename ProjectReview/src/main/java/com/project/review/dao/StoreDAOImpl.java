package com.project.review.dao;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
public class StoreDAOImpl implements StoreDAO {

	@Inject
	SqlSession sqlSession;

	private static final String mapperName = "com.project.review.StoreMapper";

	@Override
	public int insertStore(int reviewNum, String name, String addr, String lat, String lng) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("review_num", reviewNum);
		map.put("store_name", name);
		map.put("store_addr", addr);
		map.put("store_lat", lat);
		map.put("store_lng", lng);
		return sqlSession.insert(mapperName + ".insertStore", map);
	}

	@Override
	public int updateStore(int reviewNum, String name, String addr, String lat, String lng) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("review_num", reviewNum);
		map.put("store_name", name);
		map.put("store_addr", addr);
		map.put("store_lat", lat);
		map.put("store_lng", lng);
		return sqlSession.update(mapperName + ".updateStore", map);
	}

}