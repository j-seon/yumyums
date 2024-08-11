package com.yum.yumyums;

import com.yum.yumyums.dto.seller.StoreDTO;
import com.yum.yumyums.dto.user.MemberDTO;
import com.yum.yumyums.service.orders.CartService;
import com.yum.yumyums.service.seller.StoreService;
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

	private final StoreService storeService;
	private final CartService cartService;

	public StoreDTO findStoreDtoByStoreName(String storeName) {
		return storeService.findByName(storeName);
	}

	public void deleteAllPartyCartsByPartyIdAndMemberId(MemberDTO memberDTO, String partyId) {
		cartService.deleteAllPartyCartsByPartyIdAndMemberId(memberDTO, partyId);
	}

}
