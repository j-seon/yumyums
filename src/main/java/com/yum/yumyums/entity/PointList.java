package com.yum.yumyums.entity;

import jakarta.persistence.*;
import lombok.Getter;

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

    @Column(nullable = false)
    private int point = 100;

    @Column(columnDefinition = "TEXT")
    private String content = "가입 축하 선물";

    @Column(nullable = false)
    private int changePoint = 100;

    @Column(nullable = false)
    private boolean isPlus = true;
}
