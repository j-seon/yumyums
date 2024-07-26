package com.yum.yumyums.repository.seller;

import com.yum.yumyums.entity.seller.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Integer> {
	Store findByName(String storeName);
    Page<Store> findBySellerId(String sellerId, Pageable pageable);
}
