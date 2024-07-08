package com.yum.yumyums.entity.offerdeal;

import com.yum.yumyums.entity.seller.Store;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "offerdeal_id")
    private Offerdeal offerdeal;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(columnDefinition = "TEXT",nullable = false)
    private String content;

    @Column(length = 200, nullable = false)
    private String storeUrl;

    @Column(length = 50)
    private String tell;

    private LocalDateTime uploadTime;

}
