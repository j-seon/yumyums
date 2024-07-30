package com.yum.yumyums.repository.orders;

import com.yum.yumyums.entity.orders.OrdersDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersDetailRepository extends JpaRepository<OrdersDetail, Integer> {
    int getTodayTotalSalesVolumeByStoreId(int intStoreId);
}
