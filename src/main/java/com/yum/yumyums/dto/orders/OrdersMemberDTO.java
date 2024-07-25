package com.yum.yumyums.dto.orders;

import com.yum.yumyums.dto.user.MemberDTO;
import lombok.Data;

@Data
public class OrdersMemberDTO {

    private int id;
    private MemberDTO memberDTO;
    private OrdersDTO ordersDTO;
}
