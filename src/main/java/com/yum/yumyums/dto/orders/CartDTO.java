package com.yum.yumyums.dto.orders;

import com.yum.yumyums.entity.seller.Menu;
import com.yum.yumyums.entity.user.Member;
import lombok.Data;

@Data
public class CartDTO {

    private int id;
    private Member member;
    private Menu menu;
    private int menuCount;
}
