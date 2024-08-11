package com.yum.yumyums.controller;

import com.yum.yumyums.dto.TemplateData;
import com.yum.yumyums.dto.chat.PartyDTO;
import com.yum.yumyums.dto.orders.CartDTO;
import com.yum.yumyums.dto.seller.MenuDTO;
import com.yum.yumyums.dto.user.MemberDTO;
import com.yum.yumyums.service.chat.PartyService;
import com.yum.yumyums.service.orders.CartService;
import com.yum.yumyums.util.SessionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.List;


@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final PartyService partyService;

    @GetMapping
    public String getCart(Model model, HttpSession session, TemplateData templateData) {
        templateData.setViewPath("carts/cart");
        MemberDTO loginUser = (MemberDTO) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login";
        }
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

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<String> addMenuToCart(
            @RequestBody CartDTO cartDTO,
            HttpSession session
    ) {
        // 회원 정보값 받아오기
        MemberDTO loginUser = (MemberDTO) session.getAttribute("loginUser");

        // 로그인 상태가 아니라면
        if (!SessionUtil.isLoginAsMember(session)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 해주세요");
        }

        // 회원 정보값 카트에 저장
        cartDTO.setMemberDTO(loginUser);

        try {
            switch (cartDTO.getJoinPage()) {
                // 파티에서 메뉴 담기로 접근했다면
                case "party" :
                    PartyDTO partyDTO = partyService.findParty(cartDTO.getPartyDTO().getId());
                    cartDTO.setPartyDTO(partyDTO);
                    cartService.addMenuToPartyCart(cartDTO);
                    return ResponseEntity.ok("파티 장바구니에 상품이 담겼습니다");
                // 기본 주문으로 들어왔다면
                case "none" :
                    cartService.addMenuToCart(cartDTO);
                    return ResponseEntity.ok("장바구니에 상품이 담겼습니다");
                default:
                    throw new IllegalArgumentException("잘못된 방식으로 접근했습니다");
            }
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/remove")
    public String removeMenuFromCart(@RequestParam int menuId, HttpSession session) {
        MemberDTO loginUser = (MemberDTO) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login";
        }
        String memberId = loginUser.getMemberId();
        cartService.removeMenuFromCart(memberId, menuId);
        return "redirect:/cart";
    }

    @PostMapping("/update")
    public String updateMenuCount(
            @RequestParam int menuId,
            @RequestParam int menuCount,
            HttpSession session
    ) {
        MemberDTO loginUser = (MemberDTO) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login";
        }

        CartDTO cartDTO = new CartDTO();
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberId(loginUser.getMemberId());
        cartDTO.setMemberDTO(memberDTO);

        MenuDTO menuDTO = new MenuDTO();
        menuDTO.setId(menuId);
        cartDTO.setMenuDTO(menuDTO);
        cartDTO.setMenuCount(menuCount);

        cartService.updateMenuCount(cartDTO);
        return "redirect:/cart";
    }

    @GetMapping("/count")
    @ResponseBody
    public int getCartItemCount(HttpSession session) {
        MemberDTO loginUser = (MemberDTO) session.getAttribute("loginUser");
        if (loginUser == null) {
            return 0;
        }
        String memberId = loginUser.getMemberId();
        return cartService.getCartItemCount(memberId);
    }
}

