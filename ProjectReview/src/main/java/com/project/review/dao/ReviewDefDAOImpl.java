package com.project.review.dao;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.project.review.dto.ReviewDTO;

@Repository
public class ReviewDefDAOImpl implements ReviewDefDAO {

	@Inject
	SqlSession sqlSession;

	private static final String mapperName = "com.project.review.ReviewDefMapper";

	@Override
	public int insertReview(ReviewDTO review) {
		sqlSession.insert(mapperName + ".insertReview", review);
		return review.getReview_num();
	}

	@Override
	public Map<String, Object> selectReviewNum(int member_num, int review_num) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("member_num", member_num);
		map.put("review_num", review_num);
		return sqlSession.selectOne(mapperName + ".selectReviewNum", map);
	}

	@Override
	public int updateReview(int review_num, int member_num, int category_num, String review_title, String review_content, String title_color, int review_grade) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("review_num", review_num);
		map.put("member_num", member_num);
		map.put("category_num", category_num);
		map.put("review_title", review_title);
		map.put("review_content", review_content);
		map.put("title_color", title_color);
		map.put("review_grade", review_grade);
		return sqlSession.update(mapperName + ".updateReview", map);
	}

	@Override
	public int updateReviewContentImgUrl(String original_url, String update_url, int review_num) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("original_url", original_url);
		map.put("update_url", update_url);
		map.put("review_num", review_num);
		return sqlSession.update(mapperName + ".updateReviewContentImgUrl", map);
	}

	@Override
	public int updateReviewTitleImgUrl(String title_img, int review_num) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title_img", title_img);
		map.put("review_num", review_num);
		return sqlSession.update(mapperName + ".updateReviewTitleImgUrl", map);
	}

	@Override
	public int deleteReview(int review_num, int member_num) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("review_num", review_num);
		map.put("member_num", member_num);

		sqlSession.delete("com.project.review.ReviewHeartMapper.deleteWithReview", map);
		sqlSession.delete("com.project.review.ReviewReplyMapper.deleteWithReview", map);
		sqlSession.delete("com.project.review.StoreMapper.deleteStore", review_num);
		return sqlSession.delete(mapperName + ".deleteReview", map);
	}

}