package com.yum.yumyums.service;


import com.yum.yumyums.dto.FaqDTO;
import com.yum.yumyums.dto.orders.OrdersDetailDTO;
import com.yum.yumyums.entity.review.Review;
import com.yum.yumyums.repository.review.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface DashBoardService {

    List<Review> getReviewsAfter(LocalDateTime createTime) ;

    List<Review> getReviewsAfterCurrentDateTime() ;

    String getAverageRateByStoreId(int storeId) ;

    String getAverageRateBeforeTodayByStoreId(int storeId) ;

    int getTodayTotalSalesVolumeByStoreId(int intStoreId);

    long countOrdersForTodayByStoreId(int storeId);

    String getDifferBetweenTodayAndYesterDayRate(int intStoreId);

    List<HashMap<String, Object>> getOrderHashListByStoreId(int storeId);

    List<Map<String, Object>> findYearlySumsByStoreId(int intStoreId);
    List<Map<String, Object>> findMonthlySumsByStoreId(int intStoreId);
    List<Map<String, Object>> findDailySumsByStoreId(int intStoreId);

    List<OrdersDetailDTO> findAllByStoreId(int intStoreId);

    List<Map<String, Object>> findMenuInfoList(int intStoreId);


}
