package com.yum.yumyums.entity.user;

import com.yum.yumyums.entity.user.Member;
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

    @Column(columnDefinition = "DEFAULT 100", nullable = false)
    private int point;

    @Column(columnDefinition = "TEXT DEFAULT '가입 축하 선물'")
    private String content;

    @Column(columnDefinition = "DEFAULT 100", nullable = false)
    private int changePoint;

    @Column(columnDefinition = "DEFAULT true", nullable = false)
    private boolean isPlus;
}
