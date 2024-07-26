package com.yum.yumyums.service.seller;

import com.yum.yumyums.dto.seller.StoreDTO;
import com.yum.yumyums.entity.seller.Store;

import java.util.List;

public interface StoreService {
	StoreDTO findByName(String storeName);
    int getLikesForStore(int storeId);
    List<StoreDTO> getStoresOnMap();
}
