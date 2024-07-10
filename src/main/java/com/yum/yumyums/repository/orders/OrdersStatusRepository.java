package com.yum.yumyums.repository.orders;

import com.yum.yumyums.entity.orders.OrdersStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersStatusRepository extends JpaRepository<OrdersStatus, Integer> {
}
