package com.yum.yumyums.enums;

public enum FoodCategory {
	KOREAN_FOOD("KOR"),
	JAPANESE_FOOD("JPN"),
	CHINESE_FOOD("CHN"),
	WESTERN_FOOD("WESTERN"),
	SNACK("SNACK"),
	DESSERT("DESSERT"),
	ETC("ETC");

	private final String massage;
	FoodCategory(String massage) {
		this.massage = massage;
	}

	public String getMassage() {
		return massage;
	}
}
