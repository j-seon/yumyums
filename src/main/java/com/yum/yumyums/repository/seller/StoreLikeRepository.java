package com.yum.yumyums.repository.seller;


import com.yum.yumyums.entity.seller.StoreLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StoreLikeRepository extends JpaRepository<StoreLike, Integer> {
    @Query("SELECT COUNT(sl) FROM StoreLike sl WHERE sl.store.id = :storeId")
    int countLikesByStoreId(int storeId);
}
