package com.yum.yumyums.enums;

public enum FoodCategory {
	KOR("한식"),
	JPN("일식"),
	CHN("중식"),
	WESTERN("양식"),
	SNACK("분식"),
	DESSERT("디저트"),
	ETC("기타");

	private final String korName;

	FoodCategory(String korName) {
		this.korName = korName;
	}

	public String getKorName() {
		return korName;
	}
}
