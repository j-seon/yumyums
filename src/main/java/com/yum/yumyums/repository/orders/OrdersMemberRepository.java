package com.yum.yumyums.repository.orders;

import com.yum.yumyums.entity.orders.OrdersMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersMemberRepository extends JpaRepository<OrdersMember, Integer> {
}
