package com.yum.yumyums.entity.orders;

import com.yum.yumyums.entity.seller.Menu;
import com.yum.yumyums.entity.seller.Store;
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
    @JoinColumn(name = "orders_id", nullable = false)
    private Orders orders;

    //메뉴 ID
    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    //상점 ID
    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store Store;

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
