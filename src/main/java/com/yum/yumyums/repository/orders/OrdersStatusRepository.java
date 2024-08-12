package com.yum.yumyums.repository.orders;

import com.yum.yumyums.entity.orders.OrdersStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrdersStatusRepository extends JpaRepository<OrdersStatus, String> {
    OrdersStatus findStateByOrdersId(String id);
    Optional<OrdersStatus> findById(String id);
}
