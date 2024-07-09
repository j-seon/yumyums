package com.yum.yumyums.dto.user;

import com.yum.yumyums.entity.user.Member;
import lombok.Data;

@Data
public class PointListDTO {

    private int id;
    private Member member;
    private int point;
    private String content;
    private int changePoint;
    private boolean isPlus = true;
}
