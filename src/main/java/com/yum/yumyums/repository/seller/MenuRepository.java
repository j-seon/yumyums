package com.yum.yumyums.repository.seller;

import com.yum.yumyums.entity.seller.Menu;
import com.yum.yumyums.enums.FoodCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface MenuRepository extends JpaRepository<Menu, Integer> {

        List<Menu> findByStoreId(int storeId);


        @Query("SELECT m FROM Menu m WHERE m.isActive = true " +
                "AND (:categories IS NULL OR m.category IN :categories) " +
                "AND (:priceRanges IS NULL OR " +
                "(m.price <= 10000 AND 'below_10000' IN :priceRanges) OR " +
                "(m.price > 10000 AND m.price <= 20000 AND '10000_20000' IN :priceRanges) OR " +
                "(m.price > 20000 AND 'above_20000' IN :priceRanges)) " +
                "AND (:isAlone IS NULL OR m.isAlone = :isAlone)")
        List<Menu> findAllByFilters(@Param("categories") List<FoodCategory> categories,
                                    @Param("priceRanges") List<String> priceRanges,
                                    @Param("isAlone") Boolean isAlone);


        @Query("SELECT m FROM Menu m " +
                "LEFT JOIN m.store s " +
                "LEFT JOIN StoreLike sl ON sl.store.id = s.id " +
                "WHERE m.isActive = true " +
                "AND (:categories IS NULL OR m.category IN :categories) " +
                "AND (:priceRanges IS NULL OR " +
                "(m.price <= 10000 AND 'below_10000' IN :priceRanges) OR " +
                "(m.price > 10000 AND m.price <= 20000 AND '10000_20000' IN :priceRanges) OR " +
                "(m.price > 20000 AND 'above_20000' IN :priceRanges)) " +
                "AND (:isAlone IS NULL OR m.isAlone = :isAlone) " +
                "GROUP BY m.id " +
                "ORDER BY COUNT(sl.id) DESC")
        List<Menu> findAllByFiltersAndSortByLikes(@Param("categories") List<FoodCategory> categories,
                                                  @Param("priceRanges") List<String> priceRanges,
                                                  @Param("isAlone") Boolean isAlone);

        @Query("SELECT m FROM Menu m JOIN m.store s WHERE m.isActive = true " +
                "AND (:categories IS NULL OR m.category IN :categories) " +
                "AND (:priceRanges IS NULL OR " +
                "(m.price <= 10000 AND 'below_10000' IN :priceRanges) OR " +
                "(m.price > 10000 AND m.price <= 20000 AND '10000_20000' IN :priceRanges) OR " +
                "(m.price > 20000 AND 'above_20000' IN :priceRanges)) " +
                "AND (:isAlone IS NULL OR m.isAlone = :isAlone) " +
                "ORDER BY " +
                "CASE s.busy " +
                "WHEN com.yum.yumyums.enums.Busy.SPACIOUS THEN 1 " +
                "WHEN com.yum.yumyums.enums.Busy.NOMAL THEN 2 " +
                "WHEN com.yum.yumyums.enums.Busy.CROWDED THEN 3 " +
                "WHEN com.yum.yumyums.enums.Busy.FULL THEN 4 " +
                "END, " +
                "m.cookingTime ASC")
        List<Menu> findAllOrderedByStoreBusyAndCookingTime(@Param("categories") List<FoodCategory> categories,
                                                           @Param("priceRanges") List<String> priceRanges,
                                                           @Param("isAlone") Boolean isAlone);

        @Query("SELECT m FROM Menu m WHERE m.isActive = true " +
                "AND (:categories IS NULL OR m.category IN :categories) " +
                "AND (:priceRanges IS NULL OR " +
                "(m.price <= 10000 AND 'below_10000' IN :priceRanges) OR " +
                "(m.price > 10000 AND m.price <= 20000 AND '10000_20000' IN :priceRanges) OR " +
                "(m.price > 20000 AND 'above_20000' IN :priceRanges)) " +
                "AND (:isAlone IS NULL OR m.isAlone = :isAlone) " +
                "ORDER BY m.price ASC")
        List<Menu> findAllOrderedByPrice(@Param("categories") List<FoodCategory> categories,
                                         @Param("priceRanges") List<String> priceRanges,
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


        @Query("SELECT m FROM Menu m WHERE m.store.id = :storeId ORDER BY m.id desc")
        List<Menu> findMenusByStoreIdOrderedByIdDesc(int storeId);
}




