package com.yum.yumyums.dto.orders;

import com.yum.yumyums.dto.seller.MenuDTO;
import com.yum.yumyums.dto.user.MemberDTO;
import lombok.Data;

@Data
public class CartDTO {

    private int id;
    private MemberDTO memberDTO;
    private MenuDTO menuDTO;
    private int menuCount;
    private int totalPrice;
}
