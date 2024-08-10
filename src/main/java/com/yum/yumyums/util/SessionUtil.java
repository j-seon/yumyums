package com.yum.yumyums.util;

import jakarta.servlet.http.HttpSession;
import lombok.Getter;

// 세션에 들어가는 상수 정보값들을 저장하고 세션의 정보체크를 돕는 클래스
@Getter
public class SessionUtil {
	public static final String MEMBER_DTO_SESSION_ATTRIBUTE_NAME = "loginUser";
	public static final String LOGIN_TYPE_SELLER = "s";
	public static final String LOGIN_TYPE_MEMBER = "m";

	public static boolean isLogin(HttpSession session) {
		return session.getAttribute(MEMBER_DTO_SESSION_ATTRIBUTE_NAME) != null;
	}

	public static boolean isLoginAsSeller(HttpSession session) {
		String loginType = (String) session.getAttribute("loginType");
		return isLogin(session)
				&& loginType != null
				&& loginType.equals(LOGIN_TYPE_SELLER);
	}

	public static boolean isLoginAsMember(HttpSession session) {
		String loginType = (String) session.getAttribute("loginType");
		return isLogin(session)
				&& loginType != null
				&& loginType.equals(LOGIN_TYPE_MEMBER);
	}

	public static boolean isLoginAsStore(HttpSession session) {
		String loginType = (String) session.getAttribute("loginType");
		String storeId = String.valueOf(session.getAttribute("storeId"));
		return isLogin(session)
				&& loginType != null
				&& loginType.equals(LOGIN_TYPE_SELLER)
				&& storeId != null;
	}

}
