package com.project.review.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
public class MainDAOImpl implements MainDAO {

	@Inject
	SqlSession sqlSession;

	private static final String mapperName = "com.project.review.MainMapper";

	@Override
	public List<HashMap<String, Object>> selectMainMap(String lat, String lng) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lat", lat);
		map.put("lng", lng);
		return sqlSession.selectList(mapperName + ".selectMainMap", map);
	}

	@Override
	public List<HashMap<String, Object>> selectMainMapReviewInfo(String lat, String lng) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lat", lat);
		map.put("lng", lng);
		return sqlSession.selectList(mapperName + ".selectMainMapReviewInfo", map);
	}

	@Override
	public List<HashMap<String, Object>> selectMainMapReviewSearch(String[] keyword, int member_num) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("keyword", keyword);
		map.put("member_num", member_num);
		return sqlSession.selectList(mapperName + ".selectMainMapReviewSearch", map);
	}

	@Override
	public List<HashMap<String, Object>> selectTop4ReviewList() {
		return sqlSession.selectList(mapperName + ".selectTop4ReviewList");
	}

	@Override
	public List<HashMap<String, Object>> lastMonthBestUser() {
		return sqlSession.selectList(mapperName + ".lastMonthBestUser");
	}

	@Override
	public List<HashMap<String, Object>> selectUserList(int member_num, String searchTxtField) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("member_num", member_num);
		map.put("searchTxtField", searchTxtField);
		return sqlSession.selectList(mapperName + ".selectUserList", map);
	}

}