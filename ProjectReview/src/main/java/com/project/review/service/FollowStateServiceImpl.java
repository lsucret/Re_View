package com.project.review.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.project.review.dao.FollowStateDAO;

@Service
public class FollowStateServiceImpl implements FollowStateService {

	@Inject
	FollowStateDAO stateDAO;

	public int viewFollowState(int member_num, int blogger_num) {
		return stateDAO.viewFollowState(member_num, blogger_num);
	}

}