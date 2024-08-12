package com.yum.yumyums.repository.orders;

import com.yum.yumyums.entity.orders.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface OrdersRepository extends JpaRepository<Orders, String> {
    @Query("SELECT COALESCE(SUM(o.totalPrice - o.discount), 0)  " +
            "FROM Orders o " +
            "WHERE o.ordersTime >= :startOfDay " +
            "AND o.ordersTime < :endOfDay " +
            "AND o.store.id = :storeId")
    Integer findTotalSalesForTodayByStoreId(@Param("storeId") int storeId,
                                            @Param("startOfDay") LocalDateTime startOfDay,
                                            @Param("endOfDay") LocalDateTime endOfDay);

    // 금일 주문 건수
    @Query("SELECT COALESCE(COUNT(o), 0) FROM Orders o WHERE o.ordersTime >= :startOfDay AND o.ordersTime < :endOfDay AND o.store.id = :storeId")
    Long countOrdersForTodayByStoreId(@Param("storeId") int storeId,
                                      @Param("startOfDay") LocalDateTime startOfDay,
                                      @Param("endOfDay") LocalDateTime endOfDay);

    //금일 주문
    /*@Query("SELECT o FROM Orders o " +
            "JOIN OrdersStatus s ON o.id = s.ordersId " +
            "WHERE s.state <> 'COMPLETE' " +
            "AND o.ordersTime >= :startOfDay " +
            "AND o.ordersTime <= :endOfDay " +  // endOfDay 수정
            "AND o.store.id = :storeId")
    List<Orders> findByStoreIdAndDay(@Param("storeId") int storeId,
                                     @Param("startOfDay") LocalDateTime startOfDay,
                                     @Param("endOfDay") LocalDateTime endOfDay);*/
    @Query("SELECT o FROM Orders o JOIN o.ordersStatus s WHERE s.state <> 'COMPLETE' AND o.ordersTime >= :startOfDay AND o.ordersTime <= :endOfDay AND o.store.id = :storeId")
    List<Orders> findByStoreIdAndDay(@Param("storeId") int storeId, @Param("startOfDay") LocalDateTime startOfDay, @Param("endOfDay") LocalDateTime endOfDay);

    // SQL 쿼리로 일별 매출액을 계산
    @Query(value = "SELECT DATE_FORMAT(orders_time, '%Y-%m-%d') AS date, " +
            "SUM(total_price - discount) AS totalPrice " +
            "FROM orders " +
            "WHERE store_id = :storeId " +
            "GROUP BY DATE_FORMAT(orders_time, '%Y-%m-%d') " +
            "ORDER BY DATE_FORMAT(orders_time, '%Y-%m-%d')",
            nativeQuery = true)
    List<Map<String, Object>> findDailySumsByStoreId(@Param("storeId") int storeId);

    // SQL 쿼리로 월별 매출액을 계산
    @Query(value = "SELECT DATE_FORMAT(orders_time, '%Y-%m') AS date, " +
            "SUM(total_price - discount) AS totalPrice " +
            "FROM orders " +
            "WHERE store_id = :storeId " +
            "AND orders_time >= CURDATE() - INTERVAL 1 YEAR " +
            "GROUP BY DATE_FORMAT(orders_time, '%Y-%m') " +
            "ORDER BY date DESC " +
            "LIMIT 12",
            nativeQuery = true)
    List<Map<String, Object>> findMonthlySumsByStoreId(@Param("storeId") int storeId);


    // SQL 쿼리로 연도별 매출액을 계산
    @Query(value = "SELECT YEAR(orders_time) AS date, " +
            "SUM(total_price - discount) AS totalPrice " +
            "FROM orders " +
            "WHERE store_id = :storeId " +
            "GROUP BY YEAR(orders_time) " +
            "ORDER BY YEAR(orders_time)",
            nativeQuery = true)
    List<Map<String, Object>> findYearlySumsByStoreId(@Param("storeId") int storeId);

    Page<Orders> findByMemberId(String memberId, Pageable pageable);
}
