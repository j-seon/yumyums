package com.yum.yumyums.dto.offerdeal;

import com.yum.yumyums.entity.user.Member;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class OfferdealDTO {

    private int id;
    private Member member;
    private String title;
    private String content;
    private int budget;
    private LocalDateTime uploadTime;
    private LocalDateTime endTime;
    private boolean isAccept = true;
}
