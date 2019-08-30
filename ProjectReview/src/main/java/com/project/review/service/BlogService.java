package com.project.review.service;

import com.project.review.dto.FollowStateDTO;

/**
 * 블로그 메인 화면 세팅
 */

public interface BlogService {

	public FollowStateDTO selectFollowCnt(int bloggerNum); // 블로그 사이드 메뉴 팔로수 출력

	public void updateBlogMainURL(String blog_main, int mNum);

}