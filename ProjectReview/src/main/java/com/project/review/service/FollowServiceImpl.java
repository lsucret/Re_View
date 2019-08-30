package com.project.review.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.project.review.dao.FollowDAO;
import com.project.review.dto.MemberDTO;

@Service
public class FollowServiceImpl implements FollowService {

	@Inject
	FollowDAO followDAO;

	@Override
	public List<MemberDTO> selectFollowList(int meNum) throws Exception {
		return followDAO.selectFollowList(meNum);
	}

	@Override
	public List<MemberDTO> selectFollowerList(int meNum) throws Exception {
		return followDAO.selectFollowerList(meNum);
	}

	@Override
	public int followNunfollow(int member_num, String blogger_url, String state) {
		return followDAO.followNunfollow(member_num, blogger_url, state);
	}

}