package com.project.review.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.project.review.dao.StoreDAO;

@Service
public class StoreServiceImpl implements StoreService {

	@Inject
	StoreDAO storeDAO;

	@Override
	public int insertStore(int reviewNum, String name, String addr, String lat, String lng) {
		return storeDAO.insertStore(reviewNum, name, addr, lat, lng);
	}

	@Override
	public int updateStore(int reviewNum, String name, String addr, String lat, String lng) {
		return storeDAO.updateStore(reviewNum, name, addr, lat, lng);
	}

}