package com.yum.yumyums.dto.timedeal;

import com.yum.yumyums.entity.timedeal.Timedeal;
import com.yum.yumyums.entity.user.Member;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TimedealOrdersDTO {

    private Timedeal timedeal;
    private Member member;
    private int totalPrice;
    private int discount;
    private LocalDateTime ordersTime;
    private boolean isUsed = false;
}
