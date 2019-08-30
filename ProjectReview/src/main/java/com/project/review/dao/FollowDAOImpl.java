package com.project.review.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.project.review.dto.MemberDTO;

@Repository
public class FollowDAOImpl implements FollowDAO {

	@Inject
	SqlSession sqlSession;

	private static final String mapperName = "com.project.review.FollowMapper";

	@Override
	public List<MemberDTO> selectFollowList(int meNum) throws Exception {
		return sqlSession.selectList(mapperName + ".selectFollowList", meNum);
	}

	@Override
	public List<MemberDTO> selectFollowerList(int meNum) throws Exception {
		return sqlSession.selectList(mapperName + ".selectFollowerList", meNum);
	}

	@Override
	public int followNunfollow(int member_num, String blogger_url, String state) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("member_num", member_num);
		map.put("blogger_url", blogger_url);

		if (state.equals("언팔로우")) {
			return sqlSession.delete(mapperName + ".deleteFollow", map);
		} else {
			return sqlSession.insert(mapperName + ".insertFollow", map);
		}
	}

}