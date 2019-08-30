package com.project.review.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.project.review.dto.MessageDTO;

@Repository
public class MessageDAOImpl implements MessageDAO {

	@Inject
	SqlSession sqlSession;

	private static final String mapperName = "com.project.review.MessageMapper";

	public void sendMessage(MessageDTO messageDTO) {
		sqlSession.insert(mapperName + ".sendMessage", messageDTO);
	}

	public List<MessageDTO> messageList(int memberNum, String message_read, String tab) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("member_num", memberNum);
		map.put("tab", tab);
		if (message_read != null) {
			map.put("message_read", 0);
		}
		return sqlSession.selectList(mapperName + ".messageList", map);
	}

	public MessageDTO messageDetail(int memberNum, int messageNum, String tab) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("member_num", memberNum);
		map.put("message_num", messageNum);
		map.put("tab", tab);
		return sqlSession.selectOne(mapperName + ".messageDetail", map);
	}

	public void updateMessageReadCheck(int memberNum, int messageNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("member_num", memberNum);
		map.put("message_num", messageNum);
		sqlSession.update(mapperName + ".updateMessageReadCheck", map);
	}

	public void deleteMessage(int messageNum, int delChk) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("message_num", messageNum);
		map.put("delChk", delChk);
		sqlSession.update(mapperName + ".updateDelChk", map);
		sqlSession.delete(mapperName + ".deleteMessage", messageNum);
	}

	public int newMessageTotalCount(int memberNum) {
		return sqlSession.selectOne(mapperName + ".newMessageTotalCount", memberNum);
	}

}