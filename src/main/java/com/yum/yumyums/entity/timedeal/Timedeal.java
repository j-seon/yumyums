package com.yum.yumyums.entity.timedeal;

import com.yum.yumyums.entity.seller.Store;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Timedeal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private int price;

    private int priceDropRate;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

}
