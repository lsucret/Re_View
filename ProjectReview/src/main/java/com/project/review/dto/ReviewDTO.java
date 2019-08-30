package com.project.review.dto;

import java.sql.Timestamp;

public class ReviewDTO {

	private int review_num;
	private int member_num;
	private int category_num;
	private String review_title;
	private String review_content;
	private String title_img;
	private String title_color;
	private int hits;
	private int review_grade;
	private Timestamp review_insertDate;
	private Timestamp review_updateDate;

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

	public int getCategory_num() {
		return category_num;
	}

	public void setCategory_num(int category_num) {
		this.category_num = category_num;
	}

	public String getReview_title() {
		return review_title;
	}

	public void setReview_title(String review_title) {
		this.review_title = review_title;
	}

	public String getReview_content() {
		return review_content;
	}

	public void setReview_content(String review_content) {
		this.review_content = review_content;
	}

	public String getTitle_img() {
		return title_img;
	}

	public void setTitle_img(String title_img) {
		this.title_img = title_img;
	}

	public String getTitle_color() {
		return title_color;
	}

	public void setTitle_color(String title_color) {
		this.title_color = title_color;
	}

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

	public int getReview_grade() {
		return review_grade;
	}

	public void setReview_grade(int review_grade) {
		this.review_grade = review_grade;
	}

	public Timestamp getReview_insertDate() {
		return review_insertDate;
	}

	public void setReview_insertDate(Timestamp review_insertDate) {
		this.review_insertDate = review_insertDate;
	}

	public Timestamp getReview_updateDate() {
		return review_updateDate;
	}

	public void setReview_updateDate(Timestamp review_updateDate) {
		this.review_updateDate = review_updateDate;
	}

}