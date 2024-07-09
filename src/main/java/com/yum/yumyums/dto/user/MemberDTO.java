package com.yum.yumyums.dto.user;

import com.yum.yumyums.enums.Gender;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberDTO {

    private String id;
    private String password;
    private String name;
    private String birth;
    private Gender gender;
    private String email;
    private String phone;
    private LocalDateTime joinTime;
    private boolean isActive;
}
