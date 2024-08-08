package com.yum.yumyums.entity.user;

import com.yum.yumyums.dto.user.MarkStationDTO;
import com.yum.yumyums.dto.user.MemberDTO;
import com.yum.yumyums.entity.Images;
import com.yum.yumyums.enums.Gender;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Table(name = "member")
public class Member {

    @Id
    @Column(length = 50)
    private String id;

    @Column(name = "password_hash", length = 100, nullable = false)
    private String password;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate birth;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(50)", nullable = false)
    private Gender gender;

    @Column(length  = 100, nullable = false)
    private String email;

    @Column(length = 100)
    private String phone;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime joinTime;

    @Column(nullable = false)
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "images_id")
    private Images images;

    public static Member dtoToEntity(MemberDTO memberDTO){
        Member member = new Member();

        member.setId(memberDTO.getMemberId());
        member.setPassword(memberDTO.getPassword());
        member.setName(memberDTO.getName());
        member.setBirth(memberDTO.getBirth());
        member.setGender(Gender.valueOf(memberDTO.getGender()));
        member.setEmail(memberDTO.getEmail());
        member.setPhone(memberDTO.getPhone());
        member.setJoinTime(memberDTO.getJoinTime());
        member.setActive(memberDTO.isActive());
        member.setImages(memberDTO.getImagesDTO().dtoToEntity());
        return member;
    }

    public MemberDTO entityToDto(){
        MemberDTO memberDTO = new MemberDTO();

        memberDTO.setMemberId(this.getId());
        memberDTO.setPassword(this.getPassword());
        memberDTO.setName(this.getName());
        memberDTO.setBirth(this.getBirth());
        memberDTO.setGender(this.getGender().name());
        memberDTO.setEmail(this.getEmail());
        memberDTO.setPhone(this.getPhone());
        memberDTO.setJoinTime(this.getJoinTime());
        memberDTO.setActive(this.isActive());

        return memberDTO;
    }



}
