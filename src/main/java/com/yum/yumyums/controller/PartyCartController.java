package com.yum.yumyums.controller;

import com.yum.yumyums.dto.TemplateData;
import com.yum.yumyums.dto.orders.CartDTO;
import com.yum.yumyums.dto.user.MemberDTO;
import com.yum.yumyums.service.orders.CartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/partyCart")
@RequiredArgsConstructor
public class PartyCartController {
	private final CartService cartService;

	// 파티 홈페이지 조회
	@GetMapping
	public String getPartyCartPage(final HttpSession session, Model model, TemplateData templateData, @RequestParam(required = false) String targetPage) {

		//TODO party에 관련하게 전면적인 수정 필요
		templateData.setViewPath("carts/party_cart.html");
		MemberDTO loginUser = (MemberDTO) session.getAttribute("loginUser");

		String memberId = loginUser.getMemberId();
		List<CartDTO> cartItems = cartService.getCartItems(memberId);


		String storeName = "";
		if (!cartItems.isEmpty()) {
			storeName = cartItems.get(0).getMenuDTO().getStoreDTO().getName();
		}

		model.addAttribute("cartItems", cartItems);
		model.addAttribute("storeName", storeName);
		model.addAttribute("templateData", templateData);
		return "template";
	}
}
