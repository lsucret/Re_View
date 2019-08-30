package com.project.review.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.project.review.dao.CategoryDAO;
import com.project.review.dto.CategoryDTO;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Inject
	CategoryDAO categoryDAO;

	@Override
	public List<CategoryDTO> categoryList() {
		return categoryDAO.categoryList();
	}

	@Override
	public int selectCategoryCodeNum(String categoryCode) {
		return categoryDAO.selectCategoryCodeNum(categoryCode);
	}

}