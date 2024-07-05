package com.yum.yumyums.entity.user;

import com.yum.yumyums.enums.FilterCategory;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "search_filter")
@Getter
public class SearchFilter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private FilterCategory filterCategory;


    @Column(nullable = false, length = 50)
    private String name;

}