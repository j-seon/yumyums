package com.yum.yumyums.enums;

public enum Busy {
	SPACIOUS("여유"),
	NOMAL("보통"),
	CROWDED("혼잡"),
	FULL("불가능");

	private final String korName;

	Busy(String korName) {
		this.korName = korName;
	}

	public String getKorName() {
		return korName;
	}
}
