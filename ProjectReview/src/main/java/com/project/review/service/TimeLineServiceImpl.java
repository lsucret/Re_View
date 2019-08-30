package com.project.review.service;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.project.review.dao.TimeLineDAO;

@Service
public class TimeLineServiceImpl implements TimeLineService {

	@Inject
	TimeLineDAO timelineDAO;

	@Override
	public List<HashMap<String, Object>> timelineList(int member_num, int start, int end) {
		return timelineDAO.timelineList(member_num, start, end);
	}

}