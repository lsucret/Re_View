package com.project.review.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReviewReplyDTO {

	private int comment_num;
	private int review_num;
	private int member_num;
	private String member_nickname;
	private String comment_content;
	private Date comment_insertDate;
	private Date comment_updateDate;
	private String member_profile;
	private String num_url;

	public int getComment_num() {
		return comment_num;
	}

	public void setComment_num(int comment_num) {
		this.comment_num = comment_num;
	}

	public int getReview_num() {
		return review_num;
	}

	public void setReview_num(int review_num) {
		this.review_num = review_num;
	}

	public int getMember_num() {
		return member_num;
	}

	public void setMember_num(int member_num) {
		this.member_num = member_num;
	}

	public String getComment_content() {
		return comment_content;
	}

	public void setComment_content(String comment_content) {
		this.comment_content = comment_content;
	}

	public Date getComment_insertDate() {
		return comment_insertDate;
	}

	public void setComment_insertDate(Date comment_insertDate) {
		this.comment_insertDate = comment_insertDate;
	}

	public String getComment_updateDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return format.format(comment_updateDate);
	}

	public void setComment_updateDate(Date comment_updateDate) {
		this.comment_updateDate = comment_updateDate;
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