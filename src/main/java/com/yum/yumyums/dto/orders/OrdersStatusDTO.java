package com.yum.yumyums.dto.orders;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.yum.yumyums.entity.orders.OrdersStatus;
import com.yum.yumyums.enums.FoodState;
import lombok.Data;

@Data
public class OrdersStatusDTO {

    private OrdersDTO ordersDTO;
    private FoodState state =FoodState.COOKING;

    public OrdersStatus dtoToEntity(){
        OrdersStatus ordersStatus = new OrdersStatus();
        ordersStatus.setOrders(this.ordersDTO.dtoToEntity());
        ordersStatus.setState(this.state);
        return ordersStatus;
    }

    @JsonGetter("stateKorName")
    public String getStateKorName() {
        return state.getKorName();
    }
}
