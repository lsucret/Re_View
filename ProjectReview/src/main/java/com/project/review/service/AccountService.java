package com.project.review.service;

import com.project.review.dto.LoginDTO;
import com.project.review.dto.MemberDTO;

/**
 * 회원 계정 관리
 */

public interface AccountService {

	public MemberDTO login(LoginDTO loginDTO) throws Exception; // 로그인

	public void loginTime(String id) throws Exception; // 로그인 시간

	public MemberDTO userAccountSearch(String id, String name, String phone) throws Exception; // 계정찾기

	public int userAccountNewPw(String id, String phone, String newpw) throws Exception; // 새 비밀번호

	public void memberLeave(MemberDTO memberDTO); // 회원탈퇴

}