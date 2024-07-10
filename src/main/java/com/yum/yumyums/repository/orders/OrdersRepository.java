package com.yum.yumyums.repository.orders;

import com.yum.yumyums.entity.orders.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, String> {
}
