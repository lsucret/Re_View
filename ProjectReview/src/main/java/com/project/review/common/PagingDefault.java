package com.project.review.common;

/**
 * 페이징 처리를 위해서 사용될 객체로, 페이지 번호와, 페이지당 출력할 개시글 수를 관리
 */

public class PagingDefault {
	private int page, column; // 페이지당 보여줄 페이지수
	private String searchTxtField;
	private String sort;
	private String category;

	public int getPageStart() { // limit 구문에서 시작 부분에 필요한 값을 반환
		return (this.page - 1) * this.column;
	}

	public PagingDefault() { // 최초 default 생성자로 기본 객체 생성시 초기값을 지정한다. (1페이지, 10개씩)
		this.page = 1; // 사용자가 세팅하지 않으면 기본 1
		this.column = 15; // 페이지당 기본 10개씩 출력하도록 세팅
		this.searchTxtField = "";
		this.sort = "insert";
		this.category = "";
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		if (page <= 0) {
			this.page = 1; // 페이지는 1페이지부터임으로 0보다 작거나 같은값일 경우 무조건 첫번째 페이지로 설정되도록 해준다.
		} else {
			this.page = page;
		}
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		if (column <= 0 || column > 100) {
			this.column = 10;
		} else {
			this.column = column;
		}
	}

	public String getSearchTxtField() {
		return searchTxtField;
	}

	public void setSearchTxtField(String searchTxtField) {
		this.searchTxtField = searchTxtField;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "PagingDefault [page=" + page + ", category=" + category + ", column=" + column + ", searchTxtField=" + searchTxtField + ", sort=" + sort + "]";
	}

}