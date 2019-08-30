package com.project.review.dao;

import java.util.HashMap;
import java.util.List;

/**
 * 타임라인
 */

public interface TimeLineDAO {

	public List<HashMap<String, Object>> timelineList(int member_num, int start, int end);

}