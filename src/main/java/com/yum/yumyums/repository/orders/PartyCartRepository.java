package com.yum.yumyums.repository.orders;

import com.yum.yumyums.entity.orders.PartyCart;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PartyCartRepository extends JpaRepository<PartyCart, Integer> {
}
