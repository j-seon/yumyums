package com.yum.yumyums.repository.seller;

import com.yum.yumyums.entity.seller.Discount;
import com.yum.yumyums.entity.seller.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<Discount, Integer> {
}
