package com.project.review.service;

import java.util.List;

import com.project.review.dto.MemberDTO;

/**
 * 이웃 리스트
 */

public interface FollowService {

	public List<MemberDTO> selectFollowList(int meNum) throws Exception; // 팔로우 리스트 출력

	public List<MemberDTO> selectFollowerList(int meNum) throws Exception; // 팔로워 리스트 출력

	public int followNunfollow(int member_num, String blogger_url, String state); // 팔로우 하기 or 팔로우 취소

}