package com.yum.yumyums.repository.seller;

import com.yum.yumyums.entity.seller.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, String> {
}
