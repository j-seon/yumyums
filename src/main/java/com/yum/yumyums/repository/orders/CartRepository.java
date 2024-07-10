package com.yum.yumyums.repository.orders;

import com.yum.yumyums.entity.orders.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Integer> {
}
