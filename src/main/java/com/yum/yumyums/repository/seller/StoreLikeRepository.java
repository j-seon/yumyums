package com.yum.yumyums.repository.seller;


import com.yum.yumyums.entity.seller.StoreLike;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StoreLikeRepository extends JpaRepository<StoreLike, Integer> {
    @Query("SELECT COUNT(sl) FROM StoreLike sl WHERE sl.store.id = :storeId")
    int countLikesByStoreId(int storeId);
    boolean existsByMemberIdAndStoreId(String memberId, int storeId);
    Page<StoreLike> findByMemberId(String memberId, Pageable pageable);

    StoreLike findByMemberIdAndStoreId(String memberId, int storeId);
}
