package com.yum.yumyums.controller;

import com.yum.yumyums.dto.TemplateData;
import com.yum.yumyums.dto.chat.PartyDTO;
import com.yum.yumyums.dto.orders.CartDTO;
import com.yum.yumyums.dto.user.MemberDTO;
import com.yum.yumyums.service.chat.PartyService;
import com.yum.yumyums.service.orders.CartService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.yum.yumyums.util.SessionUtil.MEMBER_DTO_SESSION_ATTRIBUTE_NAME;
import static com.yum.yumyums.util.SessionUtil.isLoginAsMember;

@Controller
@RequestMapping("/partyCart")
@RequiredArgsConstructor
public class PartyCartController {
	private final CartService cartService;
	private final PartyService partyService;


	// 파티 카트 조회
	@GetMapping("/{encryptedPartyId}")
	public String getPartyCartPage(final HttpSession session, Model model, TemplateData templateData, @PathVariable String encryptedPartyId, @RequestParam String joinPage) {

		//== 유효성 검사 ==//
		//소비자 회원으로 로그인중이지 않다면
		if (!isLoginAsMember(session)) {
			return "redirect:/login"; // 로그인 페이지로 이동
		}

		//회원 정보값 가져오기
		MemberDTO memberDTO = (MemberDTO) session.getAttribute(MEMBER_DTO_SESSION_ATTRIBUTE_NAME);

		//해당 파티의 파티원이 아니거나 잘못된 경로로 접근한거라면
		if(!partyService.isThisPartyMember(encryptedPartyId, memberDTO) || joinPage.equals("none")) {
			return "redirect:/party";
		}

		//== 비즈니스 로직 ==//
		// 파티, 파티 카트 정보값 가져오기
		PartyDTO partyDTO = partyService.findParty(encryptedPartyId);
		List<CartDTO> partyCartItems = cartService.getPartyCartItems(encryptedPartyId);

		// 스토어 이름값을 가져옴
		String storeName = "";
		if (!partyCartItems.isEmpty()) {
			storeName = partyCartItems.get(0).getMenuDTO().getStoreDTO().getName();
		}

		// 데이터 넣고 이동
		model.addAttribute("partyId", encryptedPartyId);
		model.addAttribute("partyDTO", partyDTO);
		model.addAttribute("partyCartItems", partyCartItems);
		model.addAttribute("storeName", storeName);

		templateData.setViewPath("carts/party_cart.html");
		model.addAttribute("templateData", templateData);
		return "template";
	}

//	@MessageMapping("/partyCart/{encryptedPartyId}") //여기로 전송되면 메서드 호출 -> WebSocketConfig prefixes 에서 적용한건 앞에 생략
//	@SendTo("/partyCart/{encryptedPartyId}")   //구독하고 있는 장소로 메시지 전송 (목적지)  -> WebSocketConfig Broker 에서 적용한건 앞에 붙어줘야됨
//	public ChatMessageDTO chat(@DestinationVariable String encryptedPartyId) {
//
//	}
}
