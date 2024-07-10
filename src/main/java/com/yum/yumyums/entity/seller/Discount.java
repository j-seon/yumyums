package com.yum.yumyums.entity.seller;

import jakarta.persistence.*;
import lombok.Getter;


@Entity
@Table(name = "discount")
@Getter
public class Discount {


    @Id
    private int id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @Column
    private int discount;


}
