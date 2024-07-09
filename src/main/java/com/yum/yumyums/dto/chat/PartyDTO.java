package com.yum.yumyums.dto.chat;

import com.yum.yumyums.entity.seller.Store;
import com.yum.yumyums.enums.PayType;
import lombok.Data;

@Data
public class PartyDTO {

    private String id;
    private Store store;
    private PayType payType;
}
