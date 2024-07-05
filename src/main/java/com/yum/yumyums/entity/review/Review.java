package com.yum.yumyums.entity.review;

import com.yum.yumyums.entity.orders.OrdersDetail;
import com.yum.yumyums.entity.user.Member;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "review")
@Getter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "orders_detail_id", nullable = false)
    private OrdersDetail orderDetail;

    @ManyToOne
    @JoinColumn(name = "orders_member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private float rate;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

}
