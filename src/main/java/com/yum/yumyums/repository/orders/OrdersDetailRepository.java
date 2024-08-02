package com.yum.yumyums.repository.orders;

import com.yum.yumyums.entity.orders.OrdersDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdersDetailRepository extends JpaRepository<OrdersDetail, Integer> {
    List<OrdersDetail> findAllByOrdersId(String id);

    List<OrdersDetail> findAllByStoreId(int storeId);

}
