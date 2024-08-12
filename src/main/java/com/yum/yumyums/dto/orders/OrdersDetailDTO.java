package com.yum.yumyums.dto.orders;

import com.yum.yumyums.dto.seller.MenuDTO;
import com.yum.yumyums.dto.seller.StoreDTO;

import lombok.Data;

@Data
public class OrdersDetailDTO {

    private int id;
    private OrdersDTO ordersDTO;
    private StoreDTO storeDTO;
    private MenuDTO menuDTO;
    private String menuName;
    private int menuPrice;
    private int menuCount;
    private boolean reviewed;

}
