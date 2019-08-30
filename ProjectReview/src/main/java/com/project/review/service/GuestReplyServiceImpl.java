package com.project.review.service;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.project.review.dao.GuestReplyDAO;

@Service
public class GuestReplyServiceImpl implements GuestReplyService {

	@Inject
	GuestReplyDAO replyDAO;

	@Override
	public List<HashMap<String, Object>> selectGuestReplyList(int guest_num, int reply_num) {
		return replyDAO.selectGuestReplyList(guest_num, reply_num);
	}

	@Override
	public int selectGuestReplyTotalCount(int guest_num, int reply_num) {
		return replyDAO.selectGuestReplyTotalCount(guest_num, reply_num);
	}

	@Override
	public int selectGuestNumFromReply(int reply_num) {
		return replyDAO.selectGuestNumFromReply(reply_num);
	}

	@Override
	public int insertGuestReply(int guest_num, int writer_num, String content) {
		return replyDAO.insertGuestReply(guest_num, writer_num, content);
	}

	@Override
	public int updateGuestReply(String content, int reply_num) {
		return replyDAO.updateGuestReply(content, reply_num);
	}

	@Override
	public int deleteGuestReply(int reply_num) {
		return replyDAO.deleteGuestReply(reply_num);
	}
}