package com.yum.yumyums.service;


import com.yum.yumyums.dto.FaqDTO;
import com.yum.yumyums.entity.review.Review;
import com.yum.yumyums.repository.review.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

public interface DashBoardService {

    public List<Review> getReviewsAfter(LocalDateTime createTime) ;

    public List<Review> getReviewsAfterCurrentDateTime() ;

    public Double getAverageRateByStoreId(int storeId) ;


    public Double getAverageRateBeforeTodayByStoreId(int storeId) ;

    int getTodayTotalSalesVolumeByStoreId(int intStoreId);
}
