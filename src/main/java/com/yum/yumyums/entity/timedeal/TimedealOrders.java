package com.yum.yumyums.entity.timedeal;

import com.yum.yumyums.entity.user.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class TimedealOrders {
    @Id
    private int id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "timedeal_id")
    private Timedeal timedeal;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private int totalPrice;
    private int discount;
    private LocalDateTime ordersTime;

    private boolean isUsed = false; // 미사용 = false, 사용 = true
}
