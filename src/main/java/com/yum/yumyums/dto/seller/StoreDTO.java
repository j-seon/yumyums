package com.yum.yumyums.dto.seller;

import com.yum.yumyums.dto.chat.PartyDTO;
import com.yum.yumyums.entity.chat.PartyMember;
import com.yum.yumyums.entity.seller.Seller;
import com.yum.yumyums.entity.seller.Store;
import com.yum.yumyums.enums.Busy;
import com.yum.yumyums.enums.FoodCategory;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class StoreDTO {

    private int storeId;
    private SellerDTO sellerDTO = null;
    private String password = null;
    private String name = null;
    private String address = null;
    private FoodCategory category = null;
    private String content = null;
    private int openTime;
    private int closeTime;
    private Busy busy = Busy.CROWDED;

    public Store dtoToEntity() {
        Store store = new Store();
        store.setId(storeId);
        store.setSeller(Seller.toSaveEntity(sellerDTO));
        store.setName(name);
        store.setAddress(address);
        store.setCategory(category);
        store.setContent(content);
        store.setOpenTime(openTime);
        store.setCloseTime(closeTime);
        store.setBusy(busy);

        return store;
    }

}
