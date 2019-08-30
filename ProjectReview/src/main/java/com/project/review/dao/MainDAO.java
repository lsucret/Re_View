package com.project.review.dao;

import java.util.HashMap;
import java.util.List;

/**
 * 메인화면
 */

public interface MainDAO {

	public List<HashMap<String, Object>> selectMainMap(String lat, String lng); // 메인에 출력될 리스트

	public List<HashMap<String, Object>> selectMainMapReviewInfo(String lat, String lng); // 커스텀오버레이 출력

	public List<HashMap<String, Object>> selectMainMapReviewSearch(String[] keyword, int member_num); // 장소 검색

	public List<HashMap<String, Object>> selectTop4ReviewList(); // 리뷰 게시글 베스트 4개 메인에 출력

	public List<HashMap<String, Object>> lastMonthBestUser(); // 지난주 베스트 유저

	public List<HashMap<String, Object>> selectUserList(int member_num, String searchTxtField); // 유저검색

}