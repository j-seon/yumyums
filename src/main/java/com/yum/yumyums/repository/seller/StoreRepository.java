package com.yum.yumyums.repository.seller;

import com.yum.yumyums.entity.seller.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Integer> {
	Store findByName(String storeName);
}
