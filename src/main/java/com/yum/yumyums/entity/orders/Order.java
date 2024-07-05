package com.yum.yumyums.entity.orders;

import com.yum.yumyums.entity.seller.Store;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "order")
@Getter
public class Order {

	//주문ID
    @Id
    @Column(length = 50)
    private String id;

    //상점 ID
    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    //총 주문액
    @Column(name = "total_price", nullable = false)
    private int totalPrice;

    //총 할인액
    @Column(name = "discount")
    private int discount;
    
    //주문시
    @Column(name = "order_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
    private LocalDateTime orderTime;

    //주문번호
    @Column(name = "waiting_num")
    private int waitingNum;
}
