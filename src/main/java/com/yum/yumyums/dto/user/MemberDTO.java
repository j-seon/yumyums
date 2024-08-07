package com.yum.yumyums.dto.user;

import com.yum.yumyums.dto.ImagesDTO;
import com.yum.yumyums.entity.user.Member;
import com.yum.yumyums.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {

    private String memberId;
    private String password;
    private String name;
    private LocalDate birth;
    private String gender;
    private String email;
    private String phone;
    private LocalDateTime joinTime;
    private boolean isActive;
    private ImagesDTO imagesDTO;
   /* private List<MarkStationDTO> markStations;*/

    public static MemberDTO entityToDto(Member member) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberId(member.getId());
        memberDTO.setPassword(member.getPassword());
        memberDTO.setName(member.getName());
        memberDTO.setBirth(member.getBirth());
        memberDTO.setGender(String.valueOf(member.getGender()));
        memberDTO.setEmail(member.getEmail());
        memberDTO.setPhone(member.getPhone());
        memberDTO.setJoinTime(member.getJoinTime());
        memberDTO.setActive(member.isActive());
        if(member.getImages() != null){
            memberDTO.setImagesDTO(member.getImages().entityToDto());
        } else {
            memberDTO.setImagesDTO(null);
        }

        return memberDTO;
    }

    public static MemberDTO toMemberDTO(Member member) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberId(member.getId());
        memberDTO.setPassword(member.getPassword());
        memberDTO.setName(member.getName());
        memberDTO.setBirth(member.getBirth());
        memberDTO.setGender(String.valueOf(member.getGender()));
        memberDTO.setEmail(member.getEmail());
        memberDTO.setPhone(member.getPhone());
        memberDTO.setJoinTime(member.getJoinTime());
        memberDTO.setActive(member.isActive());
        if(member.getImages() != null){
            memberDTO.setImagesDTO(member.getImages().entityToDto());
        } else {
            memberDTO.setImagesDTO(null);
        }
        return memberDTO;
    }


}
