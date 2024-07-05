package com.yum.yumyums.entity;

import jakarta.persistence.*;
import lombok.Getter;


@Entity
@Table(name = "discount")
@Getter
public class Discount {


    @Id
    @OneToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @Column
    private int discount;


}
