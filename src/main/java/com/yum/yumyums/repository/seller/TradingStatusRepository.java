package com.yum.yumyums.repository.seller;

import com.yum.yumyums.entity.seller.Store;
import com.yum.yumyums.entity.seller.TradingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradingStatusRepository extends JpaRepository<TradingStatus, Integer> {
}
