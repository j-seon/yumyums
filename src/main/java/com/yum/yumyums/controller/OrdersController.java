package com.yum.yumyums.controller;

import com.yum.yumyums.dto.TemplateData;
import com.yum.yumyums.dto.orders.CartDTO;
import com.yum.yumyums.dto.orders.OrdersDTO;
import com.yum.yumyums.dto.user.MemberDTO;
import com.yum.yumyums.service.orders.OrdersService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrdersController {
    private final OrdersService ordersService;

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


    @PostMapping("/success")
    public String confirmOrder(@RequestParam("paymentMethod") String paymentMethod,
                               HttpSession session, Model model, TemplateData templateData) {
        MemberDTO loginUser = (MemberDTO) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login";
        }

        templateData.setViewPath("orders/success");
        OrdersDTO order = ordersService.placeOrder(loginUser.getMemberId(), paymentMethod);
        String formattedOrderTime = order.getOrdersTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        model.addAttribute("order", order);
        model.addAttribute("formattedOrderTime", formattedOrderTime);
        return "template";
    }
}
