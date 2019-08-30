package com.project.review.service;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.project.review.common.PagingDefault;
import com.project.review.dao.GuestDAO;
import com.project.review.dto.GuestDTO;

@Service
public class GuestServiceImpl implements GuestService {

	@Inject
	GuestDAO guestbookDAO;

	@Override
	public List<HashMap<String, Object>> selectGuestList(int blogger_num, int writer_num, int hidden_check, PagingDefault pagingDefault) {
		return guestbookDAO.selectGuestList(blogger_num, writer_num, hidden_check, pagingDefault);
	}

	@Override
	public int selectGuestTotalCount(int blogger_num, int writer_num, int hidden_check, PagingDefault pagingDefault) {
		return guestbookDAO.selectGuestTotalCount(blogger_num, writer_num, hidden_check, pagingDefault);
	}

	@Override
	public int insertGuest(String num_url, int writer, String content, boolean hidden) {
		return guestbookDAO.insertGuest(num_url, writer, content, hidden);
	}

	@Override
	public int updateGuest(int guest_num, String content, int hidden_check) {
		return guestbookDAO.updateGuest(guest_num, content, hidden_check);
	}

	@Override
	public int deleteGuest(int guest_num) {
		return guestbookDAO.deleteGuest(guest_num);
	}

	@Override
	public GuestDTO selectGuestNum(int guest_num) {
		return guestbookDAO.selectGuestNum(guest_num);
	}

}