package com.yum.yumyums.controller;

import com.yum.yumyums.dto.TemplateData;
import com.yum.yumyums.dto.chat.PartyDTO;
import com.yum.yumyums.dto.orders.CartDTO;
import com.yum.yumyums.dto.orders.OrdersDTO;
import com.yum.yumyums.dto.user.MemberDTO;
import com.yum.yumyums.service.chat.PartyService;
import com.yum.yumyums.service.orders.CartService;
import com.yum.yumyums.service.orders.OrdersService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import static com.yum.yumyums.util.SessionUtil.MEMBER_DTO_SESSION_ATTRIBUTE_NAME;
import static com.yum.yumyums.util.SessionUtil.isLoginAsMember;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrdersController {
    private final OrdersService ordersService;
    private final PartyService partyService;
    private final CartService cartService;

    // 일반결제 체크아웃 페이지
    @GetMapping("/checkout")
    public String checkout(Model model, HttpSession session, TemplateData templateData) {
        templateData.setViewPath("orders/checkout");
        MemberDTO loginUser = (MemberDTO) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login";
        }

        List<CartDTO> cartItems = ordersService.getCartItems(loginUser.getMemberId());
        if (cartItems.isEmpty()) {
            return "redirect:/cart";
        }

        int totalPrice = cartItems.stream()
                .mapToInt(item -> item.getMenuDTO().getPrice() * item.getMenuCount())
                .sum();

        model.addAttribute("loginUser", loginUser);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("templateData", templateData);

        return "template";
    }

    // [파티] 파티주문 체크아웃 페이지
    @GetMapping("/{encryptedPartyId}")
    public String partyOrderCheckoutPage(Model model, HttpSession session, TemplateData templateData,  @PathVariable String encryptedPartyId) {

        //== 유효성 검사 ==//
        //소비자 회원으로 로그인중이지 않다면
        if (!isLoginAsMember(session)) {
            return "redirect:/login"; // 로그인 페이지로 이동
        }

        //회원 정보값 가져오기
        MemberDTO memberDTO = (MemberDTO) session.getAttribute(MEMBER_DTO_SESSION_ATTRIBUTE_NAME);

        //해당 파티의 파티원이 아니거나 잘못된 경로로 접근한거라면
        if(!partyService.isThisPartyMember(encryptedPartyId, memberDTO)) {
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

        int totalPrice = partyCartItems.stream()
                .mapToInt(item -> item.getMenuDTO().getPrice() * item.getMenuCount())
                .sum();

        // 데이터 넣고 이동
        model.addAttribute("memberDTO", memberDTO);
        model.addAttribute("partyDTO", partyDTO);
        model.addAttribute("partyCartItems", partyCartItems);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("partyId", encryptedPartyId);

        templateData.setViewPath("orders/party-checkout");
        model.addAttribute("templateData", templateData);
        return "template";
    }


    // 일반결제 결제완료 페이지
    @PostMapping("/success")
    public String confirmOrder(@RequestParam("paymentMethod") String paymentMethod,
                               HttpSession session, Model model, TemplateData templateData) {
        MemberDTO loginUser = (MemberDTO) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login";
        }

        templateData.setViewPath("orders/success");
        OrdersDTO order = ordersService.placeOrder(loginUser.getMemberId(), paymentMethod);

        // 주문 시간
        String formattedOrderTime = order.getOrdersTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // 예상 대기시간
        int estimatedWaitTime = ordersService.calculateEstimatedWaitTime(order);

        model.addAttribute("order", order);
        model.addAttribute("formattedOrderTime", formattedOrderTime);
        model.addAttribute("estimatedWaitTime", estimatedWaitTime);

        return "template";
    }


    // [파티] 결제 결제완료 페이지
    @PostMapping("/{encryptedPartyId}")
    public String savePartyOrder(@RequestParam("paymentMethod") String paymentMethod,
                                 HttpSession session, Model model, TemplateData templateData,
                                 @PathVariable String encryptedPartyId) {
        //== 유효성 검사 ==//
        //소비자 회원으로 로그인중이지 않다면
        if (!isLoginAsMember(session)) {
            return "redirect:/login"; // 로그인 페이지로 이동
        }

        //회원 정보값 가져오기
        MemberDTO memberDTO = (MemberDTO) session.getAttribute(MEMBER_DTO_SESSION_ATTRIBUTE_NAME);

        //해당 파티의 파티원이 아니거나 잘못된 경로로 접근한거라면
        if(!partyService.isThisPartyMember(encryptedPartyId, memberDTO)) {
            return "redirect:/party";
        }

        //== 비즈니스 로직 ==//
        // 주문 관련된 값들 가져오기
        List<CartDTO> cartDTO = cartService.getPartyCartItems(encryptedPartyId);
        int storeId = cartDTO.get(0).getMenuDTO().getStoreDTO().getStoreId();

        // Order 제작
        OrdersDTO order = new OrdersDTO();
        order.setId(UUID.randomUUID().toString());
        order.setMemberDTO(memberDTO);
        order.setStoreDTO(cartDTO.get(0).getMenuDTO().getStoreDTO());
        order.setDiscount(0);
        order.setOrdersTime(LocalDateTime.now());
        order.setWaitingNum(ordersService.generateWaitingNum(storeId));
        order.setPaymentMethod(paymentMethod);

        // 주문시각 형태변환
        String formattedOrderTime = order.getOrdersTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // 예상 대기시간
        int estimatedWaitTime = ordersService.calculateEstimatedWaitTimeForParty(order, cartDTO.get(0).getPartyDTO().getId());

        templateData.setViewPath("orders/success");
        model.addAttribute("order", order);
        model.addAttribute("formattedOrderTime", formattedOrderTime);
        model.addAttribute("estimatedWaitTime", estimatedWaitTime);
        return "template";
    }
}
