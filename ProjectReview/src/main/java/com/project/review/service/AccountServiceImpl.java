package com.project.review.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.project.review.dao.AccountDAO;
import com.project.review.dto.LoginDTO;
import com.project.review.dto.MemberDTO;

@Service
public class AccountServiceImpl implements AccountService {

	@Inject
	AccountDAO accountDAO;

	@Override
	public MemberDTO login(LoginDTO loginDTO) throws Exception {
		return accountDAO.login(loginDTO);
	}

	@Override
	public void loginTime(String id) throws Exception { // 로그인 시간
		accountDAO.loginTime(id);
	}

	@Override
	public MemberDTO userAccountSearch(String id, String name, String phone) throws Exception {
		return accountDAO.userAccountSearch(id, name, phone);
	}

	@Override
	public int userAccountNewPw(String id, String phone, String newpw) throws Exception {
		return accountDAO.userAccountNewPw(id, phone, newpw);
	}

	@Override
	public void memberLeave(MemberDTO memberDTO) {
		accountDAO.memberLeave(memberDTO);
	}

}