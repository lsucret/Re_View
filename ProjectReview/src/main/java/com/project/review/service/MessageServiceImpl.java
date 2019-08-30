package com.project.review.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.project.review.dao.MessageDAO;
import com.project.review.dto.MessageDTO;

@Service
public class MessageServiceImpl implements MessageService {

	@Inject
	MessageDAO messageDAO;

	public void sendMessage(MessageDTO messageDTO) throws Exception {
		messageDAO.sendMessage(messageDTO);
	}

	public List<MessageDTO> messageList(int memberNum, String message_read, String tab) {
		return messageDAO.messageList(memberNum, message_read, tab);
	}

	public MessageDTO messageDetail(int memberNum, int messageNum, String tab) {
		return messageDAO.messageDetail(memberNum, messageNum, tab);
	}

	public void updateMessageReadCheck(int memberNum, int messageNum) {
		messageDAO.updateMessageReadCheck(memberNum, messageNum);
	}

	public void deleteMessage(int messageNum, int delChk) {
		messageDAO.deleteMessage(messageNum, delChk);
	}

	public int newMessageTotalCount(int memberNum) {
		return messageDAO.newMessageTotalCount(memberNum);
	}

}