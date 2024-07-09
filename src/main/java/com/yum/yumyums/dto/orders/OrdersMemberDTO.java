package com.yum.yumyums.dto.orders;

import com.yum.yumyums.entity.orders.Orders;
import com.yum.yumyums.entity.user.Member;
import lombok.Data;

@Data
public class OrdersMemberDTO {

    private int id;
    private Member member;
    private Orders orders;
}
