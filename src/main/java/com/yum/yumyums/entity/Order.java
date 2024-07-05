package com.yum.yumyums.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

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
