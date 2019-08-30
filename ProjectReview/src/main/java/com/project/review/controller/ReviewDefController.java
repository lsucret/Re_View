package com.project.review.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.project.review.common.CustomFileCopy;
import com.project.review.dto.CategoryDTO;
import com.project.review.dto.MemberDTO;
import com.project.review.dto.ReviewDTO;
import com.project.review.service.CategoryService;
import com.project.review.service.ReviewDefService;
import com.project.review.service.StoreService;

/**
 * 리뷰관리 - 등록, 수정, 삭제
 */

@Controller
@RequestMapping("/blog/review/*")
public class ReviewDefController {

	@Inject
	ReviewDefService reviewService;
	@Inject
	StoreService storeService;
	@Inject
	CategoryService categoryService;

	private final String titleColorList[] = { "#EE3840", "#EE8138", "#EECE38", "#27D700", "#38C1EE", "#385FEE" };

	/* 카테고리 리스트 */
	@RequestMapping(value = "/category", method = RequestMethod.POST)
	@ResponseBody
	public List<CategoryDTO> ReviewCategory() {
		return categoryService.categoryList();
	}

	/* 리뷰 등록 */
	@RequestMapping(value = "write")
	public String ReviewWrite(Model model, HttpSession session) {
		model.addAttribute("PageTitle", "Review");
		return "review/reviewWrite";
	}

	@RequestMapping(value = "write", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> ReviewWrite(HttpServletRequest request, HttpSession session) throws Exception {
		String review_title = request.getParameter("review_title") + "", store_category = request.getParameter("category_num") + "",
				store_name = request.getParameter("store_name") + "", review_grade = request.getParameter("review_grade") + "",
				review_content = request.getParameter("review_content") + "";

		Map<String, Object> map = new HashMap<String, Object>();
		if (review_title.trim() == null || review_title.trim().equals("")) {
			map.put("result", "notitle");
			return map;
		} else if (store_category.trim() == null || store_category.trim().equals("")) {
			map.put("result", "nocategory");
			return map;
		} else if (store_name.trim() == null || store_name.trim().equals("")) {
			map.put("result", "noname");
			return map;
		}

		MemberDTO loginDTO = (MemberDTO) session.getAttribute("login");
		int categoryNum = categoryService.selectCategoryCodeNum(store_category);
		int grade = Integer.parseInt(review_grade); // 평점 형변환

		ReviewDTO review = new ReviewDTO();
		review.setMember_num(loginDTO.getMember_num());
		review.setCategory_num(categoryNum);
		review.setReview_title(review_title);
		review.setReview_content(review_content);
		review.setTitle_color(titleColorList[grade]);
		review.setReview_grade(grade);

		int rNum = reviewService.insertReview(review);

		String store_info = request.getParameter("store_info") + "";
		storeService.insertStore(rNum, store_name, store_info.split("###")[0], store_info.split("###")[1], store_info.split("###")[2]);

		// 파일 업로드 및 타이틀 사진 등록
		String titleImg = "/img/default-bg.jpg";
		new File("D:/reviewImg/review/" + loginDTO.getNum_url()).renameTo(new File("D:/reviewImg/review/" + rNum)); // 업로드 후 폴더명 변경
		File file = new File("D:\\reviewImg\\review\\" + rNum);
		if (file.exists()) {
			File[] fileList = file.listFiles();
			int locationCheck = 0;

			for (int i = 0; i < fileList.length; i++) {
				if (review_content.contains(fileList[i].getName().substring(0, fileList[i].getName().length() - 4))) {
					if (locationCheck == 0 || locationCheck >= review_content.indexOf(fileList[i].getName().substring(0, fileList[i].getName().length() - 4))) {
						locationCheck = review_content.indexOf(fileList[i].getName().substring(0, fileList[i].getName().length() - 4));
						titleImg = "/reviewImg/review/" + rNum + "/" + fileList[i].getName();
					}
				} else {
					fileList[i].delete();
				}
			}

			reviewService.updateReviewContentImgUrl("/reviewImg/review/" + loginDTO.getNum_url() + "/", "/reviewImg/review/" + rNum + "/", rNum); // db에도 업데이트
		}
		reviewService.updateReviewTitleImgUrl(titleImg, rNum);

		map.put("result", loginDTO.getBlog_main() + loginDTO.getNum_url());
		return map;
	}

	/* 리뷰 수정 */
	@RequestMapping(value = "modify/{pageNum}")
	public ModelAndView reviewModify(@PathVariable int pageNum, HttpSession session) {
		MemberDTO loginDTO = (MemberDTO) session.getAttribute("login");
		Map<String, Object> review = reviewService.selectReviewNum(loginDTO.getMember_num(), pageNum);
		if (review == null || review.size() == 0) {
			return new ModelAndView("redirect:/blog/" + loginDTO.getNum_url());
		}
		ModelAndView mv = new ModelAndView("review/reviewModify");
		mv.addObject("review", review);
		mv.addObject("PageTitle", "Review");
		return mv;
	}

	@ResponseBody
	@PostMapping(value = "modify")
	public int reviewUpdate(HttpServletRequest request, HttpSession session) throws Exception {
		String review_title = request.getParameter("review_title") + "", store_category = request.getParameter("store_category") + "",
				store_name = request.getParameter("store_name") + "", review_grade = request.getParameter("review_grade") + "",
				review_content = request.getParameter("review_content") + "", review_num = request.getParameter("review_num") + "";
		String store_info = request.getParameter("store_info") + "";
		MemberDTO loginDTO = (MemberDTO) session.getAttribute("login");

		int categoryNum = categoryService.selectCategoryCodeNum(store_category);
		int grade = Integer.parseInt(review_grade);
		int rNum = Integer.parseInt(review_num);

		storeService.updateStore(rNum, store_name, store_info.split("###")[0], store_info.split("###")[1], store_info.split("###")[2]);
		int result = reviewService.updateReview(rNum, loginDTO.getMember_num(), categoryNum, review_title, review_content, titleColorList[grade], grade);

		String titleImg = "/img/default-bg.jpg";
		if (result > 0) {
			File reviewFile = new File("D:\\reviewImg\\review\\" + rNum); // 기존 업로드된 파일
			File updateFile = new File("D:\\reviewImg\\review\\" + loginDTO.getNum_url()); // 수정하면서 업로드한 파일

			if (updateFile.exists() && updateFile.listFiles().length > 0) { // 폴더와 업로드한 이미지가 있으면
				if (reviewFile.exists()) {
					File[] fileList = updateFile.listFiles(); // 파일 체크
					for (int i = 0; i < fileList.length; i++) {
						if (review_content.contains(fileList[i].getName().substring(0, fileList[i].getName().length() - 4))) { // 등록한 게시글 내용에 업로드한 이미지가 있으면
							new CustomFileCopy("D:\\reviewImg\\review\\" + loginDTO.getNum_url() + "\\" + fileList[i].getName(),
									"D:\\reviewImg\\review\\" + rNum + "\\" + fileList[i].getName()); // 업로드한 이미지를 기존 게시글 폴더로 복사
						}
						fileList[i].delete(); // 이미지 삭제
					}
				} else {
					new File("D:/reviewImg/review/" + loginDTO.getNum_url()).renameTo(new File("D:/reviewImg/review/" + rNum)); // 업로드 후 폴더명 변경
				}

				updateFile.delete(); // 폴더 삭제
				reviewService.updateReviewContentImgUrl("/reviewImg/review/" + loginDTO.getNum_url() + "/", "/reviewImg/review/" + rNum + "/", rNum); // db 업데이트
			}

			if (reviewFile.exists()) { // 모든 이미지 복사가 이루어 졌을 때, 게시글 이미지 폴더에 이미지가 있으면
				File[] fileList = reviewFile.listFiles();
				int locationCheck = 0;

				for (int i = 0; i < fileList.length; i++) {
					if (review_content.contains(fileList[i].getName().substring(0, fileList[i].getName().length() - 4))) {
						if (locationCheck == 0 || locationCheck >= review_content.indexOf(fileList[i].getName().substring(0, fileList[i].getName().length() - 4))) {
							locationCheck = review_content.indexOf(fileList[i].getName().substring(0, fileList[i].getName().length() - 4));
							titleImg = "/reviewImg/review/" + rNum + "/" + fileList[i].getName();
						}
					} else {
						fileList[i].delete();
					}
				}

				reviewService.updateReviewTitleImgUrl(titleImg, rNum);
			}
		}

		return result;
	}

	/* 리뷰 삭제 */
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public Map<String, Object> ReviewDelete(HttpServletRequest request, HttpSession session) {
		MemberDTO loginDTO = (MemberDTO) session.getAttribute("login");
		String sendData = request.getParameter("sendData") + "";
		String splitData[] = sendData.split("/");

		Map<String, Object> map = new HashMap<String, Object>();

		int result = 0;
		String sendURL = "";

		if (splitData[splitData.length - 2].equals(loginDTO.getNum_url())) {
			result = reviewService.deleteReview(Integer.parseInt(splitData[splitData.length - 1]), loginDTO.getMember_num());
			sendURL = "/blog/" + loginDTO.getNum_url();

			File file = new File("D:\\reviewImg\\review\\" + splitData[splitData.length - 1]);
			if (file.exists()) {
				File[] deleteFolderList = file.listFiles();
				for (int i = 0; i < deleteFolderList.length; i++) {
					deleteFolderList[i].delete();
				}
				file.delete();
			}
		}

		map.put("result", result);
		map.put("sendURL", sendURL);
		return map;
	}

	/* 파일 업로드 */
	@RequestMapping("imgUpload")
	public void multiplePhotoUpload(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		try {
			String filename = request.getHeader("file-name"); // 파일명을 받는다 - 일반 원본파일명
			MemberDTO loginDTO = (MemberDTO) session.getAttribute("login");
			String filePath = "D:\\reviewImg\\review\\" + loginDTO.getNum_url() + "\\"; // 파일 기본경로
			File file = new File(filePath);
			if (!file.exists()) {
				file.mkdirs();
			}

			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			String changeFileNm = formatter.format(new Date()) + ".jpg";
			String realFileNm = filePath + changeFileNm;

			InputStream is = request.getInputStream();
			OutputStream os = new FileOutputStream(realFileNm);
			int numRead;
			byte b[] = new byte[Integer.parseInt(request.getHeader("file-size"))];
			while ((numRead = is.read(b, 0, b.length)) != -1) {
				os.write(b, 0, numRead);
			}
			if (is != null) {
				is.close();
			}
			os.flush();
			os.close();

			String sFileInfo = ""; // 파일정보
			sFileInfo += "&bNewLine=true&sFileName=" + filename; // img 태그의 title 속성을 원본파일명으로 적용시켜주기 위함
			sFileInfo += "&sFileURL=" + "/reviewImg/review/" + loginDTO.getNum_url() + "/" + changeFileNm; // 화면에서 보여질 이미지 url

			PrintWriter print = response.getWriter();
			print.print(sFileInfo);
			print.flush();
			print.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}