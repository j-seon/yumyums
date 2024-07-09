package com.yum.yumyums.dto.orders;

import com.yum.yumyums.entity.seller.Store;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class OrdersDTO {

    private String id;
    private Store store;
    private int totalPrice;
    private int discount;
    private LocalDateTime ordersTime;
    private int waitingNum;
}
