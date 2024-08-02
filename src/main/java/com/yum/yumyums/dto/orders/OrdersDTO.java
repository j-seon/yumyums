package com.yum.yumyums.dto.orders;

import com.yum.yumyums.dto.seller.StoreDTO;
import com.yum.yumyums.dto.user.MemberDTO;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class OrdersDTO {

    private String id;
    private StoreDTO storeDTO;
    private int totalPrice;
    private int discount;
    private LocalDateTime ordersTime;
    private int waitingNum;
    private MemberDTO memberDTO;
}
