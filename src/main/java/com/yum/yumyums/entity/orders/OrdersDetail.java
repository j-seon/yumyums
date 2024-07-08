package com.yum.yumyums.entity.orders;

import com.yum.yumyums.entity.seller.Menu;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "orders_detail")
@Getter
public class OrdersDetail {

    //상세ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //주문ID
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    //메뉴 ID
    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    //메뉴
    @Column(name = "menu_name",length = 50)
    private String menuName;

    //메뉴 가격
    @Column(name="menu_price")
    private int menuPrice;

    //구매한개수
    @Column(name="menu_count")
    private int menuCount;
}
