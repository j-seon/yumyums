package com.yum.yumyums.service.orders;
import com.yum.yumyums.dto.orders.CartDTO;
import com.yum.yumyums.dto.orders.OrdersDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrdersService {
    List<CartDTO> getCartItems(String memberId);
    OrdersDTO placeOrder(String memberId, String paymentMethod);
    int generateWaitingNum(int storeId);
    Page<OrdersDTO> getOrdersByMemberId(String memberId, int page, int pageSize);
    int calculateEstimatedWaitTime(OrdersDTO ordersDTO);
}
