package com.yum.yumyums.service.orders;

import com.yum.yumyums.dto.orders.CartDTO;
import com.yum.yumyums.dto.orders.OrdersDTO;
import com.yum.yumyums.entity.orders.Cart;
import com.yum.yumyums.entity.orders.Orders;
import com.yum.yumyums.entity.orders.OrdersDetail;
import com.yum.yumyums.entity.seller.Store;
import com.yum.yumyums.entity.user.Member;
import com.yum.yumyums.repository.orders.CartRepository;
import com.yum.yumyums.repository.orders.OrdersDetailRepository;
import com.yum.yumyums.repository.orders.OrdersRepository;

import com.yum.yumyums.repository.user.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class OrdersServiceImpl implements OrdersService {
    private final CartRepository cartRepository;
    private final OrdersRepository orderRepository;
    private final OrdersDetailRepository ordersDetailRepository;
    private final MemberRepository memberRepository;

    @Override
    public List<CartDTO> getCartItems(String memberId) {
        List<Cart> carts = cartRepository.findByMemberId(memberId);
        List<CartDTO> cartDTOs = new ArrayList<>();
        for (Cart cart : carts) {
            cartDTOs.add(cart.entityToDto());
        }
        return cartDTOs;
    }

    @Transactional
    @Override
    public OrdersDTO placeOrder(String memberId, String paymentMethod) {
        List<Cart> carts = cartRepository.findByMemberId(memberId);
        if (carts.isEmpty()) {
            throw new IllegalStateException("장바구니가 비어 있습니다.");
        }
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 사용자입니다."));
        Orders order = new Orders();
        order.setId(UUID.randomUUID().toString());
        order.setMember(member);
        order.setStore(carts.get(0).getMenu().getStore());
        order.setTotalPrice(carts.stream().mapToInt(c -> c.getMenu().getPrice() * c.getMenuCount()).sum());
        order.setDiscount(0);
        order.setOrdersTime(LocalDateTime.now());
        order.setWaitingNum(generateWaitingNum(order.getStore().getId()));
        order.setPaymentMethod(paymentMethod);
        orderRepository.save(order);

        for (Cart cart : carts) {
            OrdersDetail orderDetail = new OrdersDetail();
            orderDetail.setOrders(order);
            orderDetail.setMenu(cart.getMenu());
            orderDetail.setStore(cart.getMenu().getStore());
            orderDetail.setMenuName(cart.getMenu().getName());
            orderDetail.setMenuPrice(cart.getMenu().getPrice());
            orderDetail.setMenuCount(cart.getMenuCount());

            ordersDetailRepository.save(orderDetail);
        }

        cartRepository.deleteAllByMemberId(memberId);
        return order.entityToDto(); // DTO 반환
    }

    @Override
    public int generateWaitingNum(int storeId) {
        return (int) (Math.random() * 100);
    }

    @Override
    public int calculateEstimatedWaitTime(OrdersDTO ordersDTO) {
        int additionalTime = 0;

        switch (ordersDTO.getStoreDTO().getBusy()) {
            case SPACIOUS:
                additionalTime = 0;
                break;
            case NOMAL:
                additionalTime = 10;
                break;
            case CROWDED:
                additionalTime = 20;
                break;
            case FULL:
                additionalTime = 30;
                break;
        }

        List<OrdersDetail> ordersDetails = ordersDetailRepository.findByOrdersId(ordersDTO.getId());

        int finalAdditionalTime = additionalTime;
        int maxCookingTime = ordersDetails.stream()
                .mapToInt(detail -> detail.getMenu().getCookingTime() + finalAdditionalTime)
                .max()
                .orElse(0);

        return maxCookingTime;
    }
}
