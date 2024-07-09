package com.yum.yumyums.dto.offerdeal;

import com.yum.yumyums.entity.offerdeal.Offerdeal;
import com.yum.yumyums.entity.seller.Store;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OfferDTO {

    private int id;
    private Offerdeal offerdeal;
    private Store store;
    private String content;
    private String storeUrl;
    private String tell;
    private LocalDateTime uploadTime;
}
