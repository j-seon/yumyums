package com.yum.yumyums.dto.review;

import com.yum.yumyums.entity.review.Review;
import com.yum.yumyums.entity.seller.Seller;
import lombok.Data;

@Data
public class ReplyDTO {

    private int id;
    private Review review;
    private Seller seller;
    private String content;
}
