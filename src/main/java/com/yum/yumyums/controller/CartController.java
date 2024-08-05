package com.yum.yumyums.controller;

import com.yum.yumyums.dto.TemplateData;
import com.yum.yumyums.dto.orders.CartDTO;
import com.yum.yumyums.dto.seller.MenuDTO;
import com.yum.yumyums.dto.user.MemberDTO;
import com.yum.yumyums.service.orders.CartService;
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

    @GetMapping
    public String getCart(Model model, HttpSession session, TemplateData templateData) {
        templateData.setViewPath("carts/cart");
        MemberDTO loginUser = (MemberDTO) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login";
        }
        String memberId = loginUser.getMemberId();
        List<CartDTO> cartItems = cartService.getCartItems(memberId);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("templateData", templateData);
        return "template";
    }

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<String> addMenuToCart(
            @RequestBody CartDTO cartDTO,
            HttpSession session
    ) {
        MemberDTO loginUser = (MemberDTO) session.getAttribute("loginUser");
        if (loginUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 해주세요");
        }

        cartDTO.setMemberDTO(loginUser);

        try {
            cartService.addMenuToCart(cartDTO);
            return ResponseEntity.ok("장바구니에 상품이 담겼습니다");
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