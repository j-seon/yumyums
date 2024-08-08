package com.yum.yumyums.dto.review;

import com.yum.yumyums.dto.orders.OrdersDetailDTO;
import com.yum.yumyums.dto.seller.MenuDTO;
import com.yum.yumyums.dto.seller.StoreDTO;
import com.yum.yumyums.dto.user.MemberDTO;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewDTO {

    private int id;
    private OrdersDetailDTO ordersDetailDTO;
    private MemberDTO memberDTO;
    private float rate;
    private String content;
    private StoreDTO store;
    private MenuDTO menuDTO;
    private LocalDateTime createTime;
}
