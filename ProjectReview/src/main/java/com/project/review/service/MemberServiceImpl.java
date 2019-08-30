package com.project.review.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.project.review.dao.MemberDAO;
import com.project.review.dto.MemberDTO;

@Service
public class MemberServiceImpl implements MemberService {

	@Inject
	MemberDAO memberDAO;

	@Override
	public int joinMember(MemberDTO dto) {
		return memberDAO.joinMember(dto);
	}

	@Override
	public MemberDTO idDuplicateCheck(String memberId) {
		return memberDAO.idDuplicateCheck(memberId);
	}

	@Override
	public MemberDTO phoneDuplicateCheck(String userPhone) {
		return memberDAO.phoneDuplicateCheck(userPhone);
	}

	@Override
	public int updateMemberNumDecrypt(int member_num) {
		return memberDAO.updateMemberNumDecrypt(member_num);
	}

	@Override
	public MemberDTO selectMember(String loginId) throws Exception {
		return memberDAO.selectMember(loginId);
	}

	@Override
	public int updateMember(String updateName, String updateNick, String updatePhone, String updatePass, String profile, int member_num) {
		return memberDAO.updateMember(updateName, updateNick, updatePhone, updatePass, profile, member_num);
	}

}