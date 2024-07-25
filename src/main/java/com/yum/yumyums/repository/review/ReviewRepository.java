package com.yum.yumyums.repository.review;

import com.yum.yumyums.entity.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    @Query("SELECT r FROM Review r JOIN r.ordersDetail od JOIN od.menu m WHERE m.id = :menuId")
    List<Review> findByMenuId(@Param("menuId") int menuId);

}
