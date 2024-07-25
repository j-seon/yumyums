package com.yum.yumyums.service.seller;

import com.yum.yumyums.repository.seller.StoreLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final StoreLikeRepository storeLikeRepository;

    public int getLikesForStore(int storeId) {
        return storeLikeRepository.countLikesByStoreId(storeId);
    }


}


