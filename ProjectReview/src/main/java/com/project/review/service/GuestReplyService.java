package com.project.review.service;

import java.util.HashMap;
import java.util.List;

/**
 * 방명록 댓글
 */

public interface GuestReplyService {

	public List<HashMap<String, Object>> selectGuestReplyList(int guest_num, int reply_num); // 방명록 댓글 출력

	public int selectGuestReplyTotalCount(int guest_num, int reply_num); // 방명록 댓글 cnt

	public int selectGuestNumFromReply(int reply_num); // 방명록 댓글 번호로 방명록 번호 호출

	public int insertGuestReply(int guest_num, int writer_num, String content); // 방명록 댓글 등록

	public int updateGuestReply(String content, int reply_num); // 방명록 댓글 수정

	public int deleteGuestReply(int reply_num); // 방명록 댓글 삭제

}