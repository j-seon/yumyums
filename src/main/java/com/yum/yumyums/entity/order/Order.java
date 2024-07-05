package com.yum.yumyums.entity.order;

import com.yum.yumyums.entity.seller.Store;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "order")
@Getter
public class Order {

    @Id
    @Column(length = 50)
    private String id;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Column(name = "total_price", nullable = false)
    private int totalPrice;

    //TODO 나머지 칼럼 추가해야합니다

}
