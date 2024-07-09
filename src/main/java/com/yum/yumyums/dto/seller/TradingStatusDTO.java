package com.yum.yumyums.dto.seller;

import com.yum.yumyums.entity.seller.Store;
import com.yum.yumyums.enums.Busy;
import lombok.Data;

@Data
public class TradingStatusDTO {

    private Store store;
    private Busy busy;
    private boolean isOpen = true;
}
