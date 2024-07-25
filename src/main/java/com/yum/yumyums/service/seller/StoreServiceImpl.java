package com.yum.yumyums.service.seller;

import com.yum.yumyums.dto.seller.StoreDTO;
import com.yum.yumyums.entity.seller.Store;
import com.yum.yumyums.repository.seller.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.yum.yumyums.repository.seller.StoreLikeRepository;


@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final StoreLikeRepository storeLikeRepository;

	private final StoreRepository storeRepository;

	@Override
	public StoreDTO findByName(String storeName) {
		Store store = storeRepository.findByName(storeName);
		return store.entityToDto();
	}

    public int getLikesForStore(int storeId) {
        return storeLikeRepository.countLikesByStoreId(storeId);
    }
}
