package com.yum.yumyums.repository.review;

import com.yum.yumyums.entity.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
}
