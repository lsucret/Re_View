package com.project.review.dto;

import java.sql.Timestamp;

public class GuestReplyDTO {

	private int reply_num;
	private int guest_num;
	private int member_num;
	private String reply_content;
	private Timestamp reply_insertDate;
	private Timestamp reply_updateDate;

	public int getReply_num() {
		return reply_num;
	}

	public void setReply_num(int reply_num) {
		this.reply_num = reply_num;
	}

	public int getGuest_num() {
		return guest_num;
	}

	public void setGuest_num(int guest_num) {
		this.guest_num = guest_num;
	}

	public int getMember_num() {
		return member_num;
	}

	public void setMember_num(int member_num) {
		this.member_num = member_num;
	}

	public String getReply_content() {
		return reply_content;
	}

	public void setReply_content(String reply_content) {
		this.reply_content = reply_content;
	}

	public Timestamp getReply_insertDate() {
		return reply_insertDate;
	}

	public void setReply_insertDate(Timestamp reply_insertDate) {
		this.reply_insertDate = reply_insertDate;
	}

	public Timestamp getReply_updateDate() {
		return reply_updateDate;
	}

	public void setReply_updateDate(Timestamp reply_updateDate) {
		this.reply_updateDate = reply_updateDate;
	}

}