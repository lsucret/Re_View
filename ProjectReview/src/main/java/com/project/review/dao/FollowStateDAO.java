package com.project.review.dao;

/**
 * 이웃 여부 체크
 */

public interface FollowStateDAO {

	public int viewFollowState(int member_num, int blogger_num); // 팔로우 했는지 체크하는 부분

}