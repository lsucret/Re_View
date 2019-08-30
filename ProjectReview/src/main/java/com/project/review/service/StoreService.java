package com.project.review.service;

/**
 * 리뷰장소
 */

public interface StoreService {

	public int insertStore(int reviewNum, String name, String addr, String lat, String lng);

	public int updateStore(int reviewNum, String name, String addr, String lat, String lng);

}