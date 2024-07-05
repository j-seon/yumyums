package com.yum.yumyums.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "cart")
@Getter
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @Column(columnDefinition = "DEFAULT 1", nullable = false)
    private int menuCount;

}
