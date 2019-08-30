package com.project.review.dao;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.project.review.dto.MemberDTO;

@Repository
public class MemberDefDAOImpl implements MemberDefDAO {

	@Inject
	SqlSession sqlSession;

	private static final String mapperName = "com.project.review.MemberDefMapper";

	@Override
	public int selectMemerNumDecrypt(String num_url) {
		return sqlSession.selectOne(mapperName + ".selectMemerNumDecrypt", num_url);
	}

	@Override
	public String selectBlogURL(String num_url) {
		return sqlSession.selectOne(mapperName + ".selectBlogURL", num_url);
	}

	@Override
	public MemberDTO selectMemberNum(int member_num) {
		return sqlSession.selectOne(mapperName + ".selectMemberNum", member_num);
	}
}