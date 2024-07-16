package com.yum.yumyums;

import com.yum.yumyums.service.user.MemberService;

public class APIGateway {
	private final MemberService memberService;

	public APIGateway(MemberService memberService) {
		this.memberService = memberService;
	}

	public boolean isValidMember(String memberId) {
		return memberService.isValidMember(memberId);
	}
}
