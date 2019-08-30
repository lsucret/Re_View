package com.project.review.dao;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.project.review.dto.MemberDTO;

@Repository
public class MemberDAOImpl implements MemberDAO {

	@Inject
	SqlSession sqlSession;

	private static final String mapperName = "com.project.review.MemberMapper";

	@Override
	public int joinMember(MemberDTO dto) {
		sqlSession.insert(mapperName + ".joinMember", dto);
		return dto.getMember_num();
	}

	@Override
	public MemberDTO idDuplicateCheck(String memberId) {
		return sqlSession.selectOne(mapperName + ".idDuplicateCheck", memberId);
	}

	@Override
	public MemberDTO phoneDuplicateCheck(String userPhone) {
		return sqlSession.selectOne(mapperName + ".phoneDuplicateCheck", userPhone);
	}

	@Override
	public int updateMemberNumDecrypt(int member_num) {
		return sqlSession.update(mapperName + ".updateMemberNumDecrypt", member_num);
	}

	@Override
	public MemberDTO selectMember(String loginId) throws Exception {
		return sqlSession.selectOne(mapperName + ".selectMember", loginId);
	}

	@Override
	public int updateMember(String updateName, String updateNick, String updatePhone, String updatePass, String profile, int member_num) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("member_name", updateName);
		map.put("member_nickname", updateNick);
		map.put("member_phone", updatePhone);
		map.put("member_pw", updatePass);
		map.put("member_profile", profile);
		map.put("member_num", member_num);
		return sqlSession.update(mapperName + ".updateMember", map);
	}

}