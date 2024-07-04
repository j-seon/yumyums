package com.yum.yumyums.entity;

import jakarta.persistence.*;
import lombok.Getter;


@Entity
@Table(name = "menu")
@Getter
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(nullable = false, length = 50)
    private String name;

    //TODO 나머지 컬럼들 만들어야 합니다.

}
