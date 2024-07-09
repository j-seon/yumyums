package com.yum.yumyums.dto.orders;

import com.yum.yumyums.enums.FoodState;
import lombok.Data;

@Data
public class OrdersStatusDTO {

    private int ordersId;
    private FoodState state;
}
