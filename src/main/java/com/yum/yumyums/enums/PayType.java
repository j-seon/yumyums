package com.yum.yumyums.enums;

public enum PayType {
	DUTCH("각자결제"), //각자 먹은 것
	SPLIT("분할결제"), // n분의 1
	ONCE("일괄결제"), // 선택된 한명이 일괄
	RANDOM_ONCE("랜덤 일괄결제"); // 랜덤한 한명이 일괄
	
	private final String korName;

	PayType(String korName) {
		this.korName = korName;
	}

	public String getKorName() {
		return korName;
	}
}
