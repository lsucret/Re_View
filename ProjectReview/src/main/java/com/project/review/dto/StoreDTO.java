package com.project.review.dto;

public class StoreDTO {

	private int store_num;
	private int review_num;
	private String store_name;
	private String store_addr;
	private String store_lat;
	private String store_lng;

	public int getStore_num() {
		return store_num;
	}

	public void setStore_num(int store_num) {
		this.store_num = store_num;
	}

	public int getReview_num() {
		return review_num;
	}

	public void setReview_num(int review_num) {
		this.review_num = review_num;
	}

	public String getStore_name() {
		return store_name;
	}

	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}

	public String getStore_addr() {
		return store_addr;
	}

	public void setStore_addr(String store_addr) {
		this.store_addr = store_addr;
	}

	public String getStore_lat() {
		return store_lat;
	}

	public void setStore_lat(String store_lat) {
		this.store_lat = store_lat;
	}

	public String getStore_lng() {
		return store_lng;
	}

	public void setStore_lng(String store_lng) {
		this.store_lng = store_lng;
	}

}