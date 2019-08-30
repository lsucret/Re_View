package com.project.review.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.project.review.dto.GuestReplyDTO;

@Repository
public class GuestReplyDAOImpl implements GuestReplyDAO {

	@Inject
	SqlSession sqlSession;

	private static final String mapperName = "com.project.review.GuestReplyMapper";

	@Override
	public List<HashMap<String, Object>> selectGuestReplyList(int guest_num, int reply_num) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (guest_num > 0) {
			map.put("guest_num", guest_num);
		} else if (reply_num > 0) {
			map.put("reply_num", reply_num);
		}

		return sqlSession.selectList(mapperName + ".selectGuestReplyList", map);
	}

	@Override
	public int selectGuestReplyTotalCount(int guest_num, int reply_num) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("guest_num", guest_num);
		map.put("reply_num", reply_num);
		return sqlSession.selectOne(mapperName + ".selectGuestReplyTotalCount", map);
	}

	@Override
	public int selectGuestNumFromReply(int reply_num) {
		return sqlSession.selectOne(mapperName + ".selectGuestNumFromReply", reply_num);
	}

	@Override
	public int insertGuestReply(int guest_num, int writer, String content) {
		GuestReplyDTO replyDTO = new GuestReplyDTO();
		replyDTO.setGuest_num(guest_num);
		replyDTO.setMember_num(writer);
		replyDTO.setReply_content(content);
		sqlSession.insert(mapperName + ".insertGuestReply", replyDTO);
		return replyDTO.getReply_num();
	}

	@Override
	public int updateGuestReply(String content, int cg_num) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("reply_content", content);
		map.put("reply_num", cg_num);
		return sqlSession.update(mapperName + ".updateGuestReply", map);
	}

	@Override
	public int deleteGuestReply(int cg_num) {
		return sqlSession.delete(mapperName + ".deleteGuestReply", cg_num);
	}
}