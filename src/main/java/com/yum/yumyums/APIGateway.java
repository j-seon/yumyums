package com.yum.yumyums;

import com.yum.yumyums.service.user.MemberService;

/**
 * 컨트롤러나 서비스에서 다른 서비스의 내용을 호출할때 매핑하는 클래스
 * 타서비스를 생성하지 않음으로서 추후 MSA화 시킬 경우 서비스를 분리하기 쉽게 만듦(결합도를 낮춤)
 */
public class APIGateway {
	private final MemberService memberService;

	public APIGateway(MemberService memberService) {
		this.memberService = memberService;
	}

	public boolean isValidMember(String memberId) {
		return memberService.isValidMember(memberId);
	}
}
