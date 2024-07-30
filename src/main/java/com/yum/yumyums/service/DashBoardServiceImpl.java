package com.yum.yumyums.service;

import com.yum.yumyums.dto.FaqDTO;
import com.yum.yumyums.entity.Faq;
import com.yum.yumyums.entity.orders.Orders;
import com.yum.yumyums.entity.orders.OrdersDetail;
import com.yum.yumyums.entity.review.Review;
import com.yum.yumyums.repository.FaqRepository;
import com.yum.yumyums.repository.orders.OrdersDetailRepository;
import com.yum.yumyums.repository.orders.OrdersRepository;
import com.yum.yumyums.repository.review.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service("dashBoardService")
public class DashBoardServiceImpl implements DashBoardService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private OrdersDetailRepository ordersDetailRepository;

    public List<Review> getReviewsAfter(LocalDateTime createTime) {
        return reviewRepository.findByCreateTimeAfter(createTime);
    }

    public List<Review> getReviewsAfterCurrentDateTime() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        return getReviewsAfter(currentDateTime);
    }

    public Double getAverageRateByStoreId(int storeId) {
        return reviewRepository.findAverageByStoreId(storeId);
    }

    public Double getAverageRateBeforeTodayByStoreId(int storeId) {
        return reviewRepository.findAverageRateBeforeTodayByStoreId(storeId);
    }

    @Override
    public int getTodayTotalSalesVolumeByStoreId(int intStoreId) {
        return ordersDetailRepository.getTodayTotalSalesVolumeByStoreId(intStoreId);
    }
}
