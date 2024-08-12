package com.yum.yumyums.service.orders;
import com.yum.yumyums.dto.orders.CartDTO;
import com.yum.yumyums.dto.orders.OrdersDTO;
import java.util.List;

public interface OrdersService {
    List<CartDTO> getCartItems(String memberId);
    OrdersDTO placeOrder(String memberId, String paymentMethod);
    int generateWaitingNum(int storeId);
    int calculateEstimatedWaitTime(OrdersDTO ordersDTO);
}
