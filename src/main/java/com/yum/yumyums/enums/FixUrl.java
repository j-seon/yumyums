package com.yum.yumyums.enums;

public enum FixUrl {
	SITE_LINK("http://localhost:8080/");

	private final String url;

	FixUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}
}
