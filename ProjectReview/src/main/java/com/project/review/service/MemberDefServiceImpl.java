package com.project.review.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.project.review.dao.MemberDefDAO;
import com.project.review.dto.MemberDTO;

@Service
public class MemberDefServiceImpl implements MemberDefService {

	@Inject
	MemberDefDAO defDAO;

	@Override
	public int selectMemerNumDecrypt(String num_url) {
		return defDAO.selectMemerNumDecrypt(num_url);
	}

	@Override
	public String selectBlogURL(String num_url) {
		return defDAO.selectBlogURL(num_url);
	}

	@Override
	public MemberDTO selectMemberNum(int member_num) {
		return defDAO.selectMemberNum(member_num);
	}
}