package com.yum.yumyums.dto.seller;

import com.yum.yumyums.entity.seller.Seller;
import com.yum.yumyums.enums.Busy;
import com.yum.yumyums.enums.FoodCategory;
import lombok.Data;

@Data
public class StoreDTO {

    private int id;
    private SellerDTO seller;
    private String password;
    private String name;
    private String address;
    private FoodCategory category;
    private String content;
    private int openTime;
    private int closeTime;
    private Busy busy;

}
