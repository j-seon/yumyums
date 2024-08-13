package com.yum.yumyums.service.orders;

import com.yum.yumyums.dto.orders.CartDTO;
import com.yum.yumyums.dto.orders.OrdersDTO;
import com.yum.yumyums.dto.orders.OrdersDetailDTO;
import com.yum.yumyums.dto.orders.OrdersStatusDTO;
import com.yum.yumyums.dto.review.ReviewDTO;
import com.yum.yumyums.entity.orders.Cart;
import com.yum.yumyums.entity.orders.Orders;
import com.yum.yumyums.entity.orders.OrdersDetail;
import com.yum.yumyums.entity.orders.OrdersStatus;
import com.yum.yumyums.entity.review.Review;
import com.yum.yumyums.entity.seller.Store;
import com.yum.yumyums.entity.orders.PartyCart;
import com.yum.yumyums.entity.user.Member;
import com.yum.yumyums.enums.FoodState;
import com.yum.yumyums.repository.orders.CartRepository;
import com.yum.yumyums.repository.orders.OrdersDetailRepository;
import com.yum.yumyums.repository.orders.OrdersRepository;

import com.yum.yumyums.repository.orders.OrdersStatusRepository;
import com.yum.yumyums.repository.review.ReviewRepository;

import com.yum.yumyums.repository.orders.PartyCartRepository;
import com.yum.yumyums.repository.user.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrdersServiceImpl implements OrdersService {
    private final CartRepository cartRepository;
    private final OrdersRepository orderRepository;
    private final OrdersDetailRepository ordersDetailRepository;
    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;
    private final OrdersStatusRepository ordersStatusRepository;
    private final PartyCartRepository partyCartRepository;

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

        OrdersStatus ordersStatus = new OrdersStatus();
        ordersStatus.setOrders(order);
        ordersStatus.setState(FoodState.COOKING);
        order.setOrdersStatus(ordersStatus);

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
        return order.entityToDto();
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

    @Override
    public Page<OrdersDTO> getOrdersByMemberId(String memberId, int page, int pageSize) {
        Sort sort = Sort.by(Sort.Direction.DESC, "ordersTime");
        Page<Orders> ordersPage = orderRepository.findByMemberId(memberId, PageRequest.of(page, pageSize, sort));
        List<OrdersDTO> ordersDTOs = ordersPage.getContent().stream()
                .map(orders -> {
                    OrdersDTO ordersDTO = orders.entityToDto();
                    System.out.println("OrderDTO.getId: " + ordersDTO.getId());

                    OrdersStatus ordersStatus = ordersStatusRepository.findStateByOrdersId(ordersDTO.getId());
                    OrdersStatusDTO ordersStatusDTO = null;

                    if (ordersStatus != null) {
                        ordersStatusDTO = ordersStatus.entityToDto();
                    } else {
                        System.out.println("No OrdersStatus found for Order ID: " + ordersDTO.getId());
                    }
                    ordersDTO.setOrdersStatusDTO(ordersStatusDTO);

                    List<OrdersDetailDTO> ordersDetails = ordersDetailRepository.findAllByOrdersId(ordersDTO.getId()).stream()
                            .map(ordersDetail -> {
                                OrdersDetailDTO ordersDetailDTO = ordersDetail.entityToDto();
                                Optional<Review> review = Optional.ofNullable(reviewRepository.findByOrdersDetailId(ordersDetailDTO.getId()));
                                ordersDetailDTO.setReviewed(review.isPresent());
                                return ordersDetailDTO;
                            })
                            .collect(Collectors.toList());

                    // totalQty 계산
                    int totalQty = ordersDetails.stream()
                            .mapToInt(OrdersDetailDTO::getMenuCount)
                            .sum();

                    ordersDTO.setOrdersDetails(ordersDetails);
                    ordersDTO.setTotalQty(totalQty);


                    return ordersDTO;
                })
                .collect(Collectors.toList());
        return new PageImpl<>(ordersDTOs, ordersPage.getPageable(), ordersPage.getTotalElements());
    }

    @Override
    public int calculateEstimatedWaitTimeForParty(OrdersDTO ordersDTO, String encryptedPartyId) {
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

        List<PartyCart> carts = partyCartRepository.findByPartyId(encryptedPartyId);

        int finalAdditionalTime = additionalTime;
        int maxCookingTime = carts.stream()
                .mapToInt(partyCart -> partyCart.getMenu().getCookingTime() + finalAdditionalTime)
                .max()
                .orElse(0);

        return maxCookingTime;
    }
}
