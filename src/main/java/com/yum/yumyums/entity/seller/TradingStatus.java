package com.yum.yumyums.entity.seller;

import com.yum.yumyums.enums.Busy;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class TradingStatus {
    @Id
    private int id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "store_id")
    private Store store;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(50) DEFAULT 'SPACIOUS'", nullable = false)
    private Busy busy;

    private boolean isOpen = true;
}
