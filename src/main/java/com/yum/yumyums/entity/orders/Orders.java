package com.yum.yumyums.entity.orders;


import com.yum.yumyums.dto.orders.OrdersDTO;
import com.yum.yumyums.dto.orders.OrdersStatusDTO;
import com.yum.yumyums.dto.user.MemberDTO;
import com.yum.yumyums.entity.seller.Store;
import com.yum.yumyums.entity.user.Member;
import com.yum.yumyums.enums.FoodState;
import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
public class Orders {

    //주문ID
    @Id
    @Column(length = 50)
    private String id;

    //상점 ID
    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    //총 주문액
    @Column(name = "total_price", nullable = false)
    private int totalPrice;

    //총 할인액
    @Column(name = "discount")
    private int discount;

    //주문시
    @Column(name = "orders_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
    private LocalDateTime ordersTime;

    //주문번호
    @Column(name = "waiting_num")
    private int waitingNum;


    @Column(name = "payment_method")
    private String paymentMethod;

    @OneToOne(mappedBy = "orders", cascade = CascadeType.ALL)
    private OrdersStatus ordersStatus;


    public OrdersDTO entityToDto() {
        OrdersDTO ordersDTO = new OrdersDTO();
        System.out.println("this.getOrdersTime() : "+this.getOrdersTime());
        ordersDTO.setId(this.getId());
        ordersDTO.setOrdersTime(this.getOrdersTime());
        ordersDTO.setStoreDTO(this.getStore().entityToDto());
        ordersDTO.setDiscount(this.getDiscount());
        ordersDTO.setWaitingNum(this.getWaitingNum());
        ordersDTO.setTotalPrice(this.getTotalPrice());
        if (this.getOrdersStatus() != null) {
            ordersDTO.setOrdersStatusDTO(this.getOrdersStatus().entityToDto());
        } else {
            OrdersStatus defaultOrdersStatus = new OrdersStatus();

            defaultOrdersStatus.setState(FoodState.COOKING);
            ordersDTO.setOrdersStatusDTO(defaultOrdersStatus.entityToDto()); // 또는 기본값 설정
        }
        ordersDTO.setPaymentMethod(this.getPaymentMethod());
        ordersDTO.setMemberDTO(MemberDTO.entityToDto(this.member));

        return ordersDTO;
    }


}
