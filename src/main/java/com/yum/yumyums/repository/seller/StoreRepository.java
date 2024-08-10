package com.yum.yumyums.repository.seller;

import com.yum.yumyums.entity.seller.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Integer> {
	Store findByName(String storeName);
    Page<Store> findBySellerId(String sellerId, Pageable pageable);

    @Query(value = "SELECT * FROM store " +
            "WHERE (6371 * acos(cos(radians(:lat)) * cos(radians(convY)) * " +
            "cos(radians(convX) - radians(:lon)) + sin(radians(:lat)) * " +
            "sin(radians(convY)))) <= (:radius / 1000.0)", nativeQuery = true)
    List<Store> findStoresWithinRadius(@Param("lat") double lat, @Param("lon") double lon, @Param("radius") int radius);
}
