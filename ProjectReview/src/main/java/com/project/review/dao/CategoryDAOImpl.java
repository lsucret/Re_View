package com.project.review.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.project.review.dto.CategoryDTO;

@Repository
public class CategoryDAOImpl implements CategoryDAO {

	@Inject
	SqlSession sqlSession;

	private static final String mapperName = "com.project.review.CategoryMapper";

	@Override
	public List<CategoryDTO> categoryList() {
		return sqlSession.selectList(mapperName + ".categoryList");
	}

	@Override
	public int selectCategoryCodeNum(String categoryCode) {
		return sqlSession.selectOne(mapperName + ".selectCategoryCodeNum", categoryCode);
	}

}