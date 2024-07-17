package com.yum.yumyums;

import com.yum.yumyums.service.user.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 컨트롤러나 서비스에서 다른 서비스의 내용을 호출할때 매핑하는 클래스
 * 타서비스를 직접 생성하지 않음으로서 추후 MSA화 시킬 경우 서비스를 분리하기 쉽게 만듦(결합도를 낮춤)
 */
@Component
@RequiredArgsConstructor
public class APIGateway {
	// 타서비스에서 호출해야하는 클래스들 private final로 선언. (생성자로 받아오기 = @RequiredArgsConstructor)
	// 호출해야하는 메소드를 생성하고 서비스의 메소드를 return (아래는 예시)

	private final MemberService memberService;

	public boolean isValidMember(String memberId) {
		return memberService.isValidMember(memberId);
	}

}
