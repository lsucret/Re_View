package com.project.review.dao;

import com.project.review.dto.MemberDTO;

/**
 * 회원쿼리 base
 */

public interface MemberDefDAO {

	public int selectMemerNumDecrypt(String num_url); // 회원번호 복호화

	public String selectBlogURL(String num_url); // 블로그 map or list

	public MemberDTO selectMemberNum(int member_num); // 회원번호로 회원 검색

}