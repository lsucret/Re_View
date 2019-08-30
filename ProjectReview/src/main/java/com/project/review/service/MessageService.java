package com.project.review.service;

import java.util.List;

import com.project.review.dto.MessageDTO;

/**
 * 메세지
 */

public interface MessageService {

	public void sendMessage(MessageDTO messageDTO) throws Exception;

	public List<MessageDTO> messageList(int memberNum, String message_read, String tab); // 메세지 리스트

	public MessageDTO messageDetail(int memberNum, int messageNum, String tab); // 메세지 상세

	public void updateMessageReadCheck(int memberNum, int messageNum); // 메세지 읽음확인

	public void deleteMessage(int messageNum, int delChk); // 메세지 삭제

	public int newMessageTotalCount(int memberNum); // 새 메세지 카운트

}