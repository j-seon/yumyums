package com.yum.yumyums.entity.orders;

import com.yum.yumyums.entity.seller.Menu;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "orders_detail")
@Getter
public class OrdersDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "orders_id", nullable = false)
    private Orders orders;

    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    //TODO 나머지 칼럼 추가해야합니다

}
