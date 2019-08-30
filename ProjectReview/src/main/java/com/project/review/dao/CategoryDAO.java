package com.project.review.dao;

import java.util.List;

import com.project.review.dto.CategoryDTO;

/**
 * 카테고리
 */

public interface CategoryDAO {

	public List<CategoryDTO> categoryList(); // 카테고리 리스트 출력

	public int selectCategoryCodeNum(String categoryCode); // 카테고리 코드로 카테고리 번호 검색

}