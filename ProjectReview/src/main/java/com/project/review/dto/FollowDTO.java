package com.project.review.dto;

import java.sql.Date;

public class FollowDTO {

	private int follow_num;
	private int member_num;
	private int blogger_num;
	private Date follow_date;

	public int getFollow_num() {
		return follow_num;
	}

	public void setFollow_num(int follow_num) {
		this.follow_num = follow_num;
	}

	public int getMember_num() {
		return member_num;
	}

	public void setMember_num(int member_num) {
		this.member_num = member_num;
	}

	public int getBlogger_num() {
		return blogger_num;
	}

	public void setBlogger_num(int blogger_num) {
		this.blogger_num = blogger_num;
	}

	public Date getFollow_date() {
		return follow_date;
	}

	public void setFollow_date(Date follow_date) {
		this.follow_date = follow_date;
	}

}