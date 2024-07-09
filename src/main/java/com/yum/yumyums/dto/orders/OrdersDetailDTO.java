package com.yum.yumyums.dto.orders;

import com.yum.yumyums.entity.orders.Orders;
import com.yum.yumyums.entity.seller.Menu;
import lombok.Data;

@Data
public class OrdersDetailDTO {

    private int id;
    private Orders orders;
    private Menu menu;
    private String menuName;
    private int menuPrice;
    private int menuCount;
}
