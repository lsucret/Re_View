package com.project.review.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

public class MessageDTO {

	private int message_num;
	private int message_sender;
	private int message_receiver;
	private String message_title;
	private String message_content;
	private String message_sendDate;
	private int message_read;
	private String message_readDate;
	private int message_sDelChk;
	private int message_rDelChk;
	private String member_nickname;
	private String member_profile;
	private String num_url;

	public int getMessage_num() {
		return message_num;
	}

	public void setMessage_num(int message_num) {
		this.message_num = message_num;
	}

	public int getMessage_sender() {
		return message_sender;
	}

	public void setMessage_sender(int message_sender) {
		this.message_sender = message_sender;
	}

	public int getMessage_receiver() {
		return message_receiver;
	}

	public void setMessage_receiver(int message_receiver) {
		this.message_receiver = message_receiver;
	}

	public String getMessage_title() {
		return message_title;
	}

	public void setMessage_title(String message_title) {
		this.message_title = message_title;
	}

	public String getMessage_content() {
		return message_content;
	}

	public void setMessage_content(String message_content) {
		this.message_content = message_content;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+9")
	public String getMessage_sendDate() {
		return message_sendDate;
	}

	public void setMessage_sendDate(String message_sendDate) {
		this.message_sendDate = message_sendDate;
	}

	public int getMessage_read() {
		return message_read;
	}

	public void setMessage_read(int message_read) {
		this.message_read = message_read;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+9")
	public String getMessage_readDate() {
		return message_readDate;
	}

	public void setMessage_readDate(String message_readDate) {
		this.message_readDate = message_readDate;
	}

	public int getMessage_sDelChk() {
		return message_sDelChk;
	}

	public void setMessage_sDelChk(int message_sDelChk) {
		this.message_sDelChk = message_sDelChk;
	}

	public int getMessage_rDelChk() {
		return message_rDelChk;
	}

	public void setMessage_rDelChk(int message_rDelChk) {
		this.message_rDelChk = message_rDelChk;
	}

	public String getMember_nickname() {
		return member_nickname;
	}

	public void setMember_nickname(String member_nickname) {
		this.member_nickname = member_nickname;
	}

	public String getMember_profile() {
		return member_profile;
	}

	public void setMember_profile(String member_profile) {
		this.member_profile = member_profile;
	}

	public String getNum_url() {
		return num_url;
	}

	public void setNum_url(String num_url) {
		this.num_url = num_url;
	}

}