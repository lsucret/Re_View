package com.project.review.dto;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MemberDTO {

	private int member_num;
	private String member_id;
	private String member_pw;
	private String member_name;
	private String member_nickname;
	private String member_phone;
	private String member_profile;
	private String member_lastLogin; // 회원가입할때에는 공백으로 들어가야해서 String으로 지정.
	private Date member_insertDate;
	private Date member_updateDate;
	private String num_url; // 회원번호 암호화
	private String blog_main;

	private int follow_state; // 팔로우 상태

	public int getMember_num() {
		return member_num;
	}

	public void setMember_num(int member_num) {
		this.member_num = member_num;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getMember_pw() {
		return member_pw;
	}

	public void setMember_pw(String member_pw) {
		this.member_pw = member_pw;
	}

	public String getMember_name() {
		return member_name;
	}

	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}

	public String getMember_nickname() {
		return member_nickname;
	}

	public void setMember_nickname(String member_nickname) {
		this.member_nickname = member_nickname;
	}

	public String getMember_phone() {
		return member_phone;
	}

	public void setMember_phone(String member_phone) {
		this.member_phone = member_phone;
	}

	public String getMember_profile() {
		return member_profile;
	}

	public void setMember_profile(String member_profile) {
		this.member_profile = member_profile;
	}

	public String getMember_lastLogin() {
		return member_lastLogin;
	}

	public void setMember_lastLogin(String member_lastLogin) {
		this.member_lastLogin = member_lastLogin;
	}

	public Date getMember_insertDate() {
		return member_insertDate;
	}

	public void setMember_insertDate(Date member_insertDate) {
		this.member_insertDate = member_insertDate;
	}

	public Date getMember_updateDate() {
		return member_updateDate;
	}

	public void setMember_updateDate(Date member_updateDate) {
		this.member_updateDate = member_updateDate;
	}

	public String getNum_url() {
		return num_url;
	}

	public void setNum_url(String num_url) {
		this.num_url = num_url;
	}

	public String getBlog_main() {
		return blog_main;
	}

	public void setBlog_main(String blog_main) {
		this.blog_main = "/blog/" + blog_main + "/";
	}

	public int getFollow_state() {
		return follow_state;
	}

	public void setFollow_state(int follow_state) {
		this.follow_state = follow_state;
	}

	public String updateDateFormat() { // 데이트포맷을 통해 뷰에서 보여지는 시간을 수정. ex) ~.updateDateFormat()으로 사용하면 됨!!
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return format.format(member_updateDate);
	}

	public String insertDateFormat() {
		SimpleDateFormat format = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);
		return format.format(member_insertDate);
	}
}