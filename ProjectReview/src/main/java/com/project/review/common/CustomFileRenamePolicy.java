package com.project.review.common;

import java.io.File;

import com.oreilly.servlet.multipart.FileRenamePolicy;

/**
 * Rename 규칙 : IMG_numyymmddhhmmss 참조 : DefaultFileRenamePolicy 클래스
 */
public class CustomFileRenamePolicy implements FileRenamePolicy {

	int member_num;
	String timeFormat;

	// 회원 num을 받는 생성자입니다.
	public CustomFileRenamePolicy(int member_num, String timeFormat) {
		this.member_num = member_num;
		this.timeFormat = timeFormat;
	}

	// 파일명을 바꿔줍니다.
	public File rename(File f) {
		String newName = "IMG_" + member_num + timeFormat + ".jpg";
		f = new File(f.getParent(), newName);
		return f;
	}
}