package com.yum.yumyums.dto.seller;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.yum.yumyums.dto.ImagesDTO;
import com.yum.yumyums.dto.chat.PartyDTO;
import com.yum.yumyums.entity.Images;
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
    private int likes;
    private ImagesDTO imagesDTO;
    private double convX;
    private double convY;

    public Store dtoToEntity() {
        Store store = new Store();
        store.setId(storeId);
        store.setSeller(Seller.toSaveEntity(sellerDTO));
        store.setPassword(password);
        store.setName(name);
        store.setAddress(address);
        store.setCategory(category);
        store.setContent(content);
        store.setOpenTime(openTime);
        store.setCloseTime(closeTime);
        store.setBusy(busy);
        store.setConvX(convX);
        store.setConvY(convY);

        if(imagesDTO != null){
            store.setImages(imagesDTO.dtoToEntity());
        }
        return store;
    }

    @JsonGetter("categoryKorName")
    public String getCategoryKorName() {
        return category.getKorName();
    }

    @JsonGetter("busyKorName")
    public String getBusyKorName() {
        return busy.getKorName();
    }


}
