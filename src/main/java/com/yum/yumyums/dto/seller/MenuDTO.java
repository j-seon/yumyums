package com.yum.yumyums.dto.seller;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.yum.yumyums.dto.ImagesDTO;
import com.yum.yumyums.entity.seller.Menu;
import com.yum.yumyums.entity.seller.Store;
import com.yum.yumyums.enums.FoodCategory;
import lombok.Data;

@Data
public class MenuDTO {

    private int id;
    private StoreDTO storeDTO;
    private String name;
    private FoodCategory category;
    private String content;
    private int price;
    private int cookingTime;
    private String isAlone;
    private String isActive;
    private ImagesDTO imagesDTO;

    public Menu dtoToEntity(){
        Menu menu = new Menu();
        menu.setStore(storeDTO.dtoToEntity());
        menu.setName(name);
        menu.setCategory(category);
        menu.setContent(content);
        menu.setPrice(price);
        menu.setCookingTime(cookingTime);
        menu.setAlone(Boolean.parseBoolean(isAlone));
        menu.setActive(Boolean.parseBoolean(isActive));
        menu.setImages(imagesDTO.dtoToEntity());
        return menu;
    }

    @JsonGetter("categoryKorName")
    public String getCategoryKorName() {
        return category.getKorName();
    }
}
