package com.yum.yumyums.vo;

import jakarta.servlet.http.HttpSession;
import lombok.Getter;

@Getter
public class SessionUtil {
	public static final String MEMBER_ID_SESSION_ATTRIBUTE_NAME = "email";
	public static final String LOGIN_TYPE_SELLER = "s";
	public static final String LOGIN_TYPE_MEMBER = "m";

	public static boolean isLogin(HttpSession session) {
		return session.getAttribute(MEMBER_ID_SESSION_ATTRIBUTE_NAME) != null;
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

}
