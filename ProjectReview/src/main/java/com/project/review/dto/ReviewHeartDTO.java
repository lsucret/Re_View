package com.project.review.dto;

public class ReviewHeartDTO {

	private int review_num; // 리뷰넘버
	private int member_num; // 회원넘버
	private String heart_date; // Like를 찍은 날짜

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

	public String getHeart_date() {
		return heart_date;
	}

	public void setHeart_date(String heart_date) {
		this.heart_date = heart_date;
	}

}