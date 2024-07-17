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

    private int id;
    private SellerDTO sellerDTO;
    private String password;
    private String name;
    private String address;
    private FoodCategory category;
    private String content;
    private int openTime;
    private int closeTime;
    private Busy busy;

    public Store dtoToEntity() {
        Store store = new Store();
        store.setId(id);
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
