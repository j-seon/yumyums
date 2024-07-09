package com.yum.yumyums.dto.user;

import com.yum.yumyums.entity.user.Member;
import com.yum.yumyums.entity.user.SearchFilter;
import lombok.Data;

@Data
public class PresetDetailDTO {

    private int id;
    private Member member;
    private SearchFilter searchFilter;
}
