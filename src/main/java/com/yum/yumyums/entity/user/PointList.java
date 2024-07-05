package com.yum.yumyums.entity.user;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "point_list")
@Getter
public class PointList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;


    @ColumnDefault("100")
    @Column(nullable = false)
    private int point;


    @ColumnDefault("'가입 축하 선물'")
    private String content;


    @ColumnDefault("100")
    @Column(nullable = false)
    private int changePoint;


    @ColumnDefault("true")
    @Column(nullable = false)
    private boolean isPlus;
}
