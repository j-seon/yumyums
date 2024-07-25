package com.yum.yumyums.service.seller;

import com.yum.yumyums.dto.seller.StoreDTO;

public interface StoreService {
	StoreDTO findByName(String storeName);
}
