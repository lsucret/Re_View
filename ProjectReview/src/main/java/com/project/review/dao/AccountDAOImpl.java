package com.project.review.dao;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.project.review.dto.LoginDTO;
import com.project.review.dto.MemberDTO;

@Repository
public class AccountDAOImpl implements AccountDAO {

	@Inject
	SqlSession sqlSession; // mybatis 실행 객체

	private static final String mapperName = "com.project.review.AccountMapper";

	@Override
	public MemberDTO login(LoginDTO loginDTO) throws Exception {
		return sqlSession.selectOne(mapperName + ".login", loginDTO);
	}

	@Override
	public void loginTime(String id) throws Exception { // 로그인 시간
		sqlSession.update(mapperName + ".loginTime", id);
	}

	@Override
	public MemberDTO userAccountSearch(String id, String name, String phone) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("member_id", id);
		map.put("member_name", name);
		map.put("member_phone", phone);
		return sqlSession.selectOne(mapperName + ".userAccountSearch", map);
	}

	@Override
	public int userAccountNewPw(String id, String phone, String newpw) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("member_id", id);
		map.put("member_phone", phone);
		map.put("member_pw", newpw);
		return sqlSession.update(mapperName + ".userAccountNewPw", map);
	}

	@Override
	public void memberLeave(MemberDTO memberDTO) {
		sqlSession.delete(mapperName + ".memberLeave", memberDTO.getMember_num());
	}

}