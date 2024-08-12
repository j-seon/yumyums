package com.yum.yumyums.repository.orders;

import com.yum.yumyums.entity.orders.OrdersDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrdersDetailRepository extends JpaRepository<OrdersDetail, Integer> {
    List<OrdersDetail> findAllByOrdersId(String id);

    List<OrdersDetail> findAllByStoreId(int storeId);

    @Query("SELECT SUM(od.menuCount) FROM OrdersDetail od WHERE od.menu.id = :menuId")
    Integer findTotalOrdersByMenuId(@Param("menuId") int menuId);

    List<OrdersDetail> findByOrdersId(String ordersId);

}
