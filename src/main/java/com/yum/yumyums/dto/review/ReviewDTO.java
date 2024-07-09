package com.yum.yumyums.dto.review;

import com.yum.yumyums.entity.orders.OrdersDetail;
import com.yum.yumyums.entity.user.Member;
import lombok.Data;

@Data
public class ReviewDTO {

    private int id;
    private OrdersDetail ordersDetail;
    private Member member;
    private float rate;
    private String content;
}
