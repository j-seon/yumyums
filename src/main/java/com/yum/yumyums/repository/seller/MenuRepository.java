package com.yum.yumyums.repository.seller;

import com.yum.yumyums.entity.seller.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface MenuRepository extends JpaRepository<Menu, Integer> {

        //List<Menu> findByIsActiveTrue();

        @Query("SELECT m FROM Menu m WHERE m.isActive = true " +
                "AND (:category IS NULL OR m.category = :category) " +
                "AND (:priceRange IS NULL OR " +
                "(m.price <= 10000 AND :priceRange = 'below_10000') OR " +
                "(m.price > 10000 AND m.price <= 20000 AND :priceRange = '10000_20000') OR " +
                "(m.price > 20000 AND :priceRange = 'above_20000')) " +
                "AND (:isAlone IS NULL OR m.isAlone = :isAlone)")
        List<Menu> findAllByFilters(@Param("category") String category,
                                 @Param("priceRange") String priceRange,
                                 @Param("isAlone") Boolean isAlone);

        @Query("SELECT m FROM Menu m " +
                "LEFT JOIN m.store s " +
                "LEFT JOIN StoreLike sl ON sl.store.id = s.id " +
                "WHERE m.isActive = true " +
                "AND (:category IS NULL OR m.category = :category) " +
                "AND (:priceRange IS NULL OR " +
                "(m.price <= 10000 AND :priceRange = 'below_10000') OR " +
                "(m.price > 10000 AND m.price <= 20000 AND :priceRange = '10000_20000') OR " +
                "(m.price > 20000 AND :priceRange = 'above_20000')) " +
                "AND (:isAlone IS NULL OR m.isAlone = :isAlone) " +
                "GROUP BY m.id " +
                "ORDER BY COUNT(sl.id) DESC")
        List<Menu> findAllByFiltersAndSortByLikes(@Param("category") String category,
                                               @Param("priceRange") String priceRange,
                                               @Param("isAlone") Boolean isAlone);

        @Query("SELECT m FROM Menu m JOIN m.store s ORDER BY " +
                "CASE s.busy " +
                "WHEN 'SPACIOUS' THEN 1 " +
                "WHEN 'NOMAL' THEN 2 " +
                "WHEN 'CROWDED' THEN 3 " +
                "WHEN 'FULL' THEN 4 " +
                "END, " +
                "m.cookingTime ASC")
        List<Menu> findAllOrderedByStoreBusyAndCookingTime(@Param("category") String category,
                                                           @Param("priceRange") String priceRange,
                                                           @Param("isAlone") Boolean isAlone);

        @Query("SELECT m.id AS id, " +
                "m.store.id AS storeId, " +
                "m.category AS category, " +
                "m.price AS price, " +
                "m.name AS name, " +
                "m.cookingTime AS cookingTime, " +
                "m.isAlone AS isAlone, " +
                "m.isActive AS isActive, " +
                "COALESCE(SUM(od.menuCount), 0) AS orderCount, " +
                "COALESCE(AVG(r.rate), 0) AS avgRate " +
                "FROM Menu m " +
                "LEFT JOIN OrdersDetail od ON m.id = od.menu.id " +
                "LEFT JOIN Review r ON m.id = r.menu.id " +
                "WHERE m.store.id = :storeId " +
                "GROUP BY m.id, m.store.id, m.category, m.price, m.name, m.cookingTime, " +
                "m.isAlone, m.isActive")
        List<Map<String,Object>> findMenuStatsByStoreId(@Param("storeId") int storeId);


        @Query("SELECT m FROM Menu m WHERE m.store.id = :storeId ORDER BY m.category, m.name")
        Page<Menu> findMenusByStoreIdOrderedByCategory(int storeId, Pageable pageable);
}




