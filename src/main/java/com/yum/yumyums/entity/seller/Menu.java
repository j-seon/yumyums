package com.yum.yumyums.entity.seller;

import com.yum.yumyums.dto.seller.MenuDTO;
import com.yum.yumyums.entity.Images;
import com.yum.yumyums.enums.FoodCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "menu")
@Getter @Setter
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Column(nullable = false, length = 50)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private FoodCategory category;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int cookingTime;

    @Column(nullable = false)
    private boolean isAlone; //혼밥 가능여부(true = 가능)

    @Column(nullable = false)
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "images_id")
    private Images images;

    public MenuDTO entityToDto(){
        MenuDTO menuDTO = new MenuDTO();
        menuDTO.setStoreDTO(this.store.entityToDto());
        menuDTO.setId(this.getId());
        menuDTO.setName(this.getName());
        menuDTO.setCategory(this.getCategory());
        menuDTO.setStoreDTO(this.store.entityToDto());
        menuDTO.setContent(this.getContent());
        menuDTO.setPrice(this.getPrice());
        menuDTO.setCookingTime(this.getCookingTime());
        menuDTO.setIsAlone(String.valueOf(this.isAlone()));
        menuDTO.setIsActive(String.valueOf(this.isActive()));

        if (this.images != null) {
            menuDTO.setImagesDTO(this.images.entityToDto());
        } else {
            menuDTO.setImagesDTO(null);
        }
        return menuDTO;
    }
    public static Menu dtoToEntity(MenuDTO menuDTO) {
        Menu menu = new Menu();
        menu.setId(menuDTO.getId());
        menu.setName(menuDTO.getName());
        menu.setCategory(menuDTO.getCategory());
        menu.setContent(menuDTO.getContent());
        menu.setStore(menuDTO.getStoreDTO().dtoToEntity());
        menu.setPrice(menuDTO.getPrice());
        menu.setCookingTime(menuDTO.getCookingTime());
        menu.setAlone(Boolean.parseBoolean(menuDTO.getIsAlone()));
        menu.setActive(Boolean.parseBoolean(menuDTO.getIsActive()));
        return menu;
    }

}
