package com.yum.yumyums.entity.user;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "preset_detail")
@Getter
public class PresetDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "search_filter_id", nullable = false)
    private SearchFilter searchFilter;

}