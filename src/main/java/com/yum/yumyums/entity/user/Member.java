package com.yum.yumyums.entity.user;

import com.yum.yumyums.enums.Gender;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "member")
@Getter @Setter
public class Member {

    @Id
    @Column(length = 50)
    private String id;

    @Column(name = "password_hash", length = 100, nullable = false)
    private String password;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 50, nullable = false)
    private String birth;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(50)", nullable = false)
    private Gender gender;

    @Column(length  = 100, nullable = false)
    private String email;

    @Column(length = 100)
    private String phone;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
    private LocalDateTime joinTime;

    @Column(nullable = false)
    private boolean isActive;
}
