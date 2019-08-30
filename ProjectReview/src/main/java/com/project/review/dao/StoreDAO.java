package com.project.review.dao;

/**
 * 리뷰장소
 */

public interface StoreDAO {

	public int insertStore(int reviewNum, String name, String addr, String lat, String lng);

	public int updateStore(int reviewNum, String name, String addr, String lat, String lng);

}