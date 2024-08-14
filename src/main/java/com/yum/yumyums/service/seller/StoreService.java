package com.yum.yumyums.service.seller;

import com.yum.yumyums.dto.seller.StoreDTO;
import com.yum.yumyums.dto.seller.StoreLikeDTO;
import com.yum.yumyums.entity.seller.Store;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StoreService {
	StoreDTO findByName(String storeName);

    Page<StoreDTO> getStoresBySellerId(String sellerId, int page, int pageSize);

    StoreDTO loginStore(String storeName, String password);

	int getLikesForStore(int storeId);

	List<StoreDTO> getStoresOnMap();

	void save(StoreDTO storeDTO);

	StoreDTO findById(int storeId);

	List<StoreDTO> findStoresWithinRadius(double lat, double lon, int radius);

    void update(Store store);

	Page<StoreLikeDTO> getStoreLikesByMemberId(String memberId, int page, int pageSize);

    boolean isStoreLikedByMember(String memberId, int storeId);

	void saveStoreLike(String memberId, int storeId);

	void removeStoreLike(String memberId, int storeId);
}
