package com.yum.yumyums.service.seller;

import com.yum.yumyums.dto.seller.SellerDTO;
import com.yum.yumyums.entity.seller.Seller;
import com.yum.yumyums.repository.seller.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {
    private final SellerRepository sellerRepository;

    @Override
    public void save(SellerDTO sellerDTO) {
        Seller seller = Seller.toSaveEntity(sellerDTO);
        sellerRepository.save(seller);
    }

}
