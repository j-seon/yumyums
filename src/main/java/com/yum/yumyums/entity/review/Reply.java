package com.yum.yumyums.entity.review;

import com.yum.yumyums.entity.seller.Seller;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "reply")
@Getter
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private Seller seller;

    @Column(columnDefinition = "TEXT")
    private String content;
}
