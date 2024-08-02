package com.yum.yumyums.enums;

public enum FoodState {

	COOKING("조리중"), //각자 먹은 것
	READY("준비완료"), // n분의 1
	COMPLETE("수령완료"); // 랜덤한 한명이 일괄

	private final String korName;

	FoodState(String korName) {
		this.korName = korName;
	}

	public String getKorName() {
		return korName;
	}

}
