package com.project.review.dto;

import java.sql.Timestamp;

public class GuestDTO {

	private int guest_num;
	private int blogger_num;
	private int writer_num;
	private String guest_content;
	private int hidden_check;
	private Timestamp guest_insertDate;
	private Timestamp guest_updateDate;

	public int getGuest_num() {
		return guest_num;
	}

	public void setGuest_num(int guest_num) {
		this.guest_num = guest_num;
	}

	public int getBlogger_num() {
		return blogger_num;
	}

	public void setBlogger_num(int blogger_num) {
		this.blogger_num = blogger_num;
	}

	public int getWriter_num() {
		return writer_num;
	}

	public void setWriter_num(int writer_num) {
		this.writer_num = writer_num;
	}

	public String getGuest_content() {
		return guest_content;
	}

	public void setGuest_content(String guest_content) {
		this.guest_content = guest_content;
	}

	public int getHidden_check() {
		return hidden_check;
	}

	public void setHidden_check(int hidden_check) {
		this.hidden_check = hidden_check;
	}

	public Timestamp getGuest_insertDate() {
		return guest_insertDate;
	}

	public void setGuest_insertDate(Timestamp guest_insertDate) {
		this.guest_insertDate = guest_insertDate;
	}

	public Timestamp getGuest_updateDate() {
		return guest_updateDate;
	}

	public void setGuest_updateDate(Timestamp guest_updateDate) {
		this.guest_updateDate = guest_updateDate;
	}

}