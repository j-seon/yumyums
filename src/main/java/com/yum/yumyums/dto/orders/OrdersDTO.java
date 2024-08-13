package com.yum.yumyums.dto.orders;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.yum.yumyums.dto.seller.StoreDTO;
import com.yum.yumyums.dto.user.MemberDTO;
import com.yum.yumyums.entity.orders.Orders;
import com.yum.yumyums.entity.orders.OrdersDetail;
import com.yum.yumyums.entity.orders.OrdersStatus;
import com.yum.yumyums.entity.user.Member;
import com.yum.yumyums.enums.FoodState;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
public class OrdersDTO {

    private String id;
    private MemberDTO memberDTO;
    private StoreDTO storeDTO;
    private int totalPrice;
    private int discount;
    private LocalDateTime ordersTime;
    private int waitingNum;
    private List<OrdersDetailDTO> ordersDetails;
    private String paymentMethod;
    private OrdersStatusDTO ordersStatusDTO;
    private int totalQty;

    public Orders dtoToEntity(){
        Orders orders = new Orders();
        orders.setId(this.id);
        orders.setMember(Member.dtoToEntity(this.memberDTO));
        orders.setStore(this.storeDTO.dtoToEntity());
        orders.setTotalPrice(this.totalPrice);
        orders.setDiscount(this.discount);
        orders.setWaitingNum(this.waitingNum);
        orders.setPaymentMethod(this.paymentMethod);
        return orders;
    }

}
