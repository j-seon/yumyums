package com.yum.yumyums.service.seller;

import com.yum.yumyums.dto.seller.StoreDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StoreService {
	StoreDTO findByName(String storeName);

    Page<StoreDTO> getStoresBySellerId(String sellerId, int page, int pageSize);

    StoreDTO loginStore(String storeName, String password);
}
