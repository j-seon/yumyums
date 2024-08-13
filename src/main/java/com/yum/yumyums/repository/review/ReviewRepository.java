package com.yum.yumyums.repository.review;

import com.yum.yumyums.dto.review.ReviewDTO;
import com.yum.yumyums.entity.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    @Query("SELECT r FROM Review r JOIN r.ordersDetail od JOIN od.menu m WHERE m.id = :menuId")
    List<Review> findByMenuId(int menuId);

    List<Review> findByCreateTimeAfter(LocalDateTime createTime);

    // 금일 날짜를 기준으로 rate의 평균값을 계산하는 쿼리
    @Query(value="SELECT COALESCE(AVG(r.rate), 0)  FROM Review r where r.store_id = :storeId", nativeQuery = true)
    Double findAverageByStoreId(@Param("storeId") int storeId);

    // 금일 이전 날짜를 기준으로 rate의 평균값을 계산하는 쿼리
    @Query(value = "SELECT COALESCE(AVG(rate), 0) FROM review WHERE create_time < CURDATE() and store_id = :storeId", nativeQuery = true)
    Double findAverageRateBeforeTodayByStoreId(@Param("storeId") int storeId);

    Review findByOrdersDetailId(int id);
}
