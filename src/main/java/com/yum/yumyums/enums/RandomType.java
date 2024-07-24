package com.yum.yumyums.enums;

public enum RandomType {
	DICE("주사위 굴리기"), //다이스
	ROULETTE("룰렛 돌리기"); //룰렛

	private final String korName;

	RandomType(String korName) {
		this.korName = korName;
	}

	public String getKorName() {
		return korName;
	}
}
