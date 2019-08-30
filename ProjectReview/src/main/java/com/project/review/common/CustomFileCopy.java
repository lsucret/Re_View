package com.project.review.common;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class CustomFileCopy {
	String inFileName, outFileName;

	public CustomFileCopy(String inFileName, String outFileName) {
		this.inFileName = inFileName;
		this.outFileName = outFileName;

		fileCopy();
	}

	public void fileCopy() {
		try {
			FileInputStream fis = new FileInputStream(inFileName);
			FileOutputStream fos = new FileOutputStream(outFileName);

			int data = 0;
			while ((data = fis.read()) != -1) {
				fos.write(data);
			}
			fis.close();
			fos.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}