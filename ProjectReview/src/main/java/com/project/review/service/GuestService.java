package com.project.review.service;

import java.util.HashMap;
import java.util.List;

import com.project.review.common.PagingDefault;
import com.project.review.dto.GuestDTO;

/**
 * 방명록 > int 블로거 번호, int 로그인한 유저 번호, int 히든여부
 */

public interface GuestService {

	public List<HashMap<String, Object>> selectGuestList(int blogger_num, int writer_num, int hidden_check, PagingDefault pagingDefault);

	public int selectGuestTotalCount(int blogger_num, int writer_num, int hidden_check, PagingDefault pagingDefault);

	public int insertGuest(String num_url, int writer_num, String content, boolean hidden); // 방명록 등록

	public int updateGuest(int guest_num, String content, int hidden_check); // 방명록 수정

	public int deleteGuest(int guest_num); // 방명록 삭제

	public GuestDTO selectGuestNum(int guest_num); // 방명록 검색. 내용 수정할 때 데이터 호출용

}