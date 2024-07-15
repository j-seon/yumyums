package com.yum.yumyums.entity.user;

import com.yum.yumyums.dto.user.MemberDTO;
import com.yum.yumyums.enums.Gender;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    public static Member toSaveEntity(MemberDTO memberDTO){
        Member member = new Member();

        member.setId(memberDTO.getId());
        member.setPassword(memberDTO.getPassword());
        member.setName(memberDTO.getName());
        member.setBirth(memberDTO.getBirth());
        member.setGender(memberDTO.getGender());
        member.setEmail(memberDTO.getEmail());
        member.setPhone(memberDTO.getPhone());
        member.setActive(true);
        return member;
    }
}
