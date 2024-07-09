package com.yum.yumyums.entity.seller;

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

}
