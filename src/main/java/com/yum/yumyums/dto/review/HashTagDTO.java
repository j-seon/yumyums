package com.yum.yumyums.dto.review;

import com.yum.yumyums.entity.review.Review;
import lombok.Data;

@Data
public class HashTagDTO {

    private int id;
    private Review review;
    private String content;
}
