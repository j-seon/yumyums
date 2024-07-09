package com.yum.yumyums.dto.user;

import com.yum.yumyums.enums.FilterCategory;
import lombok.Data;

@Data
public class SearchFilterDTO {

    private int id;
    private FilterCategory filterCategory;
    private String name;
}
