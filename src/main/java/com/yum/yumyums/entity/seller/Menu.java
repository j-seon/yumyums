package com.yum.yumyums.entity.seller;

import com.yum.yumyums.dto.seller.MenuDTO;
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

    @Column(nullable = false, length = 50)
    private String category;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int cookingTime;

    @Column(nullable = false)
    private boolean isAlone;

    @Column(nullable = false)
    private boolean isActive;

    public MenuDTO entityToDto(){

        MenuDTO menuDTO = new MenuDTO();
        menuDTO.setStoreDTO(this.store.entityToDTO());
        menuDTO.setId(this.getId());
        menuDTO.setName(this.getName());
        menuDTO.setCategory(this.getCategory());
        menuDTO.setContent(this.getContent());
        menuDTO.setPrice(this.getPrice());
        menuDTO.setCookingTime(this.getCookingTime());
        menuDTO.setAlone(this.isAlone());
        menuDTO.setActive(this.isActive());

        return menuDTO;

    }

}
