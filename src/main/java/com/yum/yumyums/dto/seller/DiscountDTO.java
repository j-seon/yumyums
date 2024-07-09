package com.yum.yumyums.dto.seller;

import com.yum.yumyums.entity.seller.Menu;
import lombok.Data;

@Data
public class DiscountDTO {

    private Menu menu;
    private int discount;
}
