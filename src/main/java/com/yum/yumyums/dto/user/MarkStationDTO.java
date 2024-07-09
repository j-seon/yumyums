package com.yum.yumyums.dto.user;

import com.yum.yumyums.entity.user.Member;
import lombok.Data;

@Data
public class MarkStationDTO {

    private int id;
    private Member member;
    private String stationId;
    private String memo;
}
