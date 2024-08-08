package com.yum.yumyums.entity.orders;

import com.yum.yumyums.dto.orders.OrdersDetailDTO;
import com.yum.yumyums.entity.seller.Menu;
import com.yum.yumyums.entity.seller.Store;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "orders_detail")
@Data
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
    private Store store;

    //메뉴
    @Column(name = "menu_name",length = 50)
    private String menuName;

    //메뉴 가격
    @Column(name="menu_price")
    private int menuPrice;

    //구매한개수
    @Column(name="menu_count")
    private int menuCount;

    public OrdersDetailDTO entityToDto() {
        OrdersDetailDTO ordersDetailDTO = new OrdersDetailDTO();

        ordersDetailDTO.setId(this.getId());
        ordersDetailDTO.setMenuDTO(this.getMenu().entityToDto());
        ordersDetailDTO.setStoreDTO(this.getStore().entityToDto());
        ordersDetailDTO.setMenuName(this.getMenuName());
        ordersDetailDTO.setMenuPrice(this.getMenuPrice());
        ordersDetailDTO.setMenuCount(this.getMenuCount());

        return ordersDetailDTO;
    }

}

