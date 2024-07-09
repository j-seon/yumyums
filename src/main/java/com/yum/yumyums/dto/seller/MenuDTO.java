package com.yum.yumyums.dto.seller;

import com.yum.yumyums.entity.seller.Store;
import lombok.Data;

@Data
public class MenuDTO {

    private int id;
    private Store store;
    private String name;
    private String category;
    private String content;
    private int price;
    private int cookingTime;
    private boolean isAlone;
    private boolean isActive;
}
