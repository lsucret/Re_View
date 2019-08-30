package com.project.review.dao;

import com.project.review.dto.FollowStateDTO;

/**
 * 블로그 메인 화면 세팅
 */

public interface BlogDAO {

	public FollowStateDTO selectFollowCnt(int bloggerNum); // 블로그 사이드 메뉴 출력

	public void updateBlogMainURL(String blog_main, int mNum);

}