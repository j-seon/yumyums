package com.yum.yumyums.entity.offerdeal;

import com.yum.yumyums.entity.user.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Offerdeal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private int budget;

    private LocalDateTime uploadTime;

    private LocalDateTime endTime;

    private boolean isAccept = true; // true: 제안 받는 중, false: 마감
}
