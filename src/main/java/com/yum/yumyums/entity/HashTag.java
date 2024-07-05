package com.yum.yumyums.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "hashtag")
@Getter
public class HashTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;

    @Column(nullable = false, length = 10)
    private String content;
}
