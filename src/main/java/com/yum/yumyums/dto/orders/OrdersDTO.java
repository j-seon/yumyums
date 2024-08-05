package com.yum.yumyums.dto.orders;

import com.yum.yumyums.dto.seller.StoreDTO;
import com.yum.yumyums.dto.user.MemberDTO;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
public class OrdersDTO {

    private String id;
    private MemberDTO memberDTO;
    private StoreDTO storeDTO;
    private int totalPrice;
    private int discount;
    private LocalDateTime ordersTime;
    private int waitingNum;
    private List<OrdersDetailDTO> ordersDetails;
    private String paymentMethod;
}
