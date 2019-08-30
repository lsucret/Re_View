package com.project.review.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.project.review.common.PagingDefault;
import com.project.review.dto.GuestDTO;

@Repository
public class GuestDAOImpl implements GuestDAO {

	@Inject
	SqlSession sqlSession;

	private static final String mapperName = "com.project.review.GuestMapper";

	@Override
	public List<HashMap<String, Object>> selectGuestList(int blogger_num, int writer_num, int hidden_check, PagingDefault pagingDefault) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("blogger_num", blogger_num);
		map.put("writer_num", writer_num);
		map.put("hidden_check", hidden_check);
		map.put("start", pagingDefault.getPageStart());
		map.put("end", pagingDefault.getColumn());
		map.put("searchTxtField", pagingDefault.getSearchTxtField());
		return sqlSession.selectList(mapperName + ".selectGuestList", map);
	}

	@Override
	public int selectGuestTotalCount(int blogger_num, int writer_num, int hidden_check, PagingDefault pagingDefault) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("blogger_num", blogger_num);
		map.put("writer_num", writer_num);
		map.put("hidden_check", hidden_check);
		map.put("searchTxtField", pagingDefault.getSearchTxtField());
		return sqlSession.selectOne(mapperName + ".selectGuestTotalCount", map);
	}

	@Override
	public int insertGuest(String num_url, int writer_num, String content, boolean hidden) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("num_url", num_url);
		map.put("writer_num", writer_num);
		map.put("content", content);
		if (hidden) {
			map.put("hidden_check", 1);
		} else {
			map.put("hidden_check", 0);
		}
		return sqlSession.insert(mapperName + ".insertGuest", map);
	}

	@Override
	public int updateGuest(int gnum, String content, int hidden_check) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("guest_num", gnum);
		map.put("guest_content", content);
		map.put("hidden_check", hidden_check);
		return sqlSession.update(mapperName + ".updateGuest", map);
	}

	@Override
	public int deleteGuest(int guest_num) {
		sqlSession.delete("com.project.review.GuestReplyMapper.deleteGuestReplyGuest", guest_num);
		return sqlSession.delete(mapperName + ".deleteGuest", guest_num);
	}

	@Override
	public GuestDTO selectGuestNum(int guest_num) {
		return sqlSession.selectOne(mapperName + ".selectGuestNum", guest_num);
	}

}