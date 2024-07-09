package com.yum.yumyums.dto.timedeal;

import com.yum.yumyums.entity.seller.Store;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TimedealDTO {

    private int id;
    private Store store;
    private String title;
    private String content;
    private int price;
    private int priceDropRate;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
