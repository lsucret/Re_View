package com.project.review.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.project.review.dto.ReviewReplyDTO;

@Repository
public class ReviewReplyDAOImpl implements ReviewReplyDAO {

	@Inject
	SqlSession sqlSession;

	private static final String mapperName = "com.project.review.ReviewReplyMapper";

	@Override
	public List<ReviewReplyDTO> selectReviewReply(int review_num, int start) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("review_num", review_num);
		map.put("start", start);
		return sqlSession.selectList(mapperName + ".selectReviewReply", map);
	}

	@Override
	public int selectReivewReplyCount(int review_num) {
		return sqlSession.selectOne(mapperName + ".selectReivewReplyCount", review_num);
	}

	@Override
	public void writeReply(ReviewReplyDTO replyDTO) {
		sqlSession.insert(mapperName + ".writeReply", replyDTO);
	}

	@Override
	public void modifyReply(ReviewReplyDTO replyDTO) throws Exception {
		sqlSession.update(mapperName + ".modifyReply", replyDTO);
	}

	@Override
	public void deleteReply(Integer comment_num) throws Exception {
		sqlSession.delete(mapperName + ".deleteReply", comment_num);
	}

}