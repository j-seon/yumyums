package com.yum.yumyums.entity.order;

import com.yum.yumyums.entity.seller.Menu;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "order_detail")
@Getter
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    //TODO 나머지 칼럼 추가해야합니다

}
