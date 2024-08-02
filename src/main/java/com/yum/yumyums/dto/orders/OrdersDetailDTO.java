package com.yum.yumyums.dto.orders;

import com.yum.yumyums.dto.seller.MenuDTO;
import com.yum.yumyums.dto.seller.StoreDTO;
import com.yum.yumyums.entity.seller.Store;
import lombok.Data;

@Data
public class OrdersDetailDTO {

    private int id;
    private OrdersDTO ordersDTO;
    private MenuDTO menuDTO;
    private StoreDTO storeDTO;
    private String menuName;
    private int menuPrice;
    private int menuCount;
}
