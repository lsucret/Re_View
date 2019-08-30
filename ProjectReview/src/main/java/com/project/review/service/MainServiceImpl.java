package com.project.review.service;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.project.review.dao.MainDAO;

@Service
public class MainServiceImpl implements MainService {

	@Inject
	MainDAO mainDAO;

	@Override
	public List<HashMap<String, Object>> selectMainMap(String lat, String lng) {
		return mainDAO.selectMainMap(lat, lng);
	}

	@Override
	public List<HashMap<String, Object>> selectMainMapReviewInfo(String lat, String lng) {
		return mainDAO.selectMainMapReviewInfo(lat, lng);
	}

	@Override
	public List<HashMap<String, Object>> selectMainMapReviewSearch(String[] keyword, int member_num) {
		return mainDAO.selectMainMapReviewSearch(keyword, member_num);
	}

	@Override
	public List<HashMap<String, Object>> selectTop4ReviewList() {
		return mainDAO.selectTop4ReviewList();
	}

	@Override
	public List<HashMap<String, Object>> lastMonthBestUser() {
		return mainDAO.lastMonthBestUser();
	}

	@Override
	public List<HashMap<String, Object>> selectUserList(int member_num, String searchTxtField) {
		return mainDAO.selectUserList(member_num, searchTxtField);
	}

}