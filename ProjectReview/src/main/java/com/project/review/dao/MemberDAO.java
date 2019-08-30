package com.project.review.dao;

import com.project.review.dto.MemberDTO;

/**
 * 회원관리 > 가입 및 수정
 */

public interface MemberDAO {

	public int joinMember(MemberDTO dto); // 회원가입

	public MemberDTO idDuplicateCheck(String memberId); // 아이디 중복체크

	public MemberDTO phoneDuplicateCheck(String userPhone); // 휴대폰 중복체크

	public int updateMemberNumDecrypt(int member_num); // 회원번호 암호화

	public MemberDTO selectMember(String loginId) throws Exception; // 로그인 회원 정보 출력

	public int updateMember(String updateName, String updateNick, String updatePhone, String updatePass, String profile, int member_num); // 로그인 회원 정보 수정

}