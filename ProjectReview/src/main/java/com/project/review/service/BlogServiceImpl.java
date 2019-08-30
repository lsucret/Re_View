package com.project.review.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.project.review.dao.BlogDAO;
import com.project.review.dto.FollowStateDTO;

@Service
public class BlogServiceImpl implements BlogService {

	@Inject
	BlogDAO setupDAO;

	@Override
	public FollowStateDTO selectFollowCnt(int bloggerNum) {
		return setupDAO.selectFollowCnt(bloggerNum);
	}

	@Override
	public void updateBlogMainURL(String blog_main, int mNum) {
		setupDAO.updateBlogMainURL(blog_main, mNum);
	}

}