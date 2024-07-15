package com.yum.yumyums.dto.user;

import com.yum.yumyums.entity.user.Member;
import com.yum.yumyums.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {

    private String id;
    private String password;
    private String name;
    private LocalDate birth;
    private Gender gender;
    private String email;
    private String phone;
    private LocalDateTime joinTime;
    private boolean isActive;

    public static MemberDTO toMemberDTO(Member member) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(member.getId());
        memberDTO.setPassword(member.getPassword());
        memberDTO.setName(member.getName());
        memberDTO.setBirth(member.getBirth());
        memberDTO.setGender(member.getGender());
        memberDTO.setEmail(member.getEmail());
        memberDTO.setPhone(member.getPhone());
        memberDTO.setJoinTime(member.getJoinTime());
        memberDTO.setActive(member.isActive());
        return memberDTO;
    }
}
