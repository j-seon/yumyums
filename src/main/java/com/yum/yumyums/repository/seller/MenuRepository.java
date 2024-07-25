package com.yum.yumyums.repository.seller;

import com.yum.yumyums.entity.seller.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Integer> {

        //List<Menu> findByIsActiveTrue();

        @Query("SELECT m FROM Menu m WHERE m.isActive = true " +
                "AND (:category IS NULL OR m.category = :category) " +
                "AND (:priceRange IS NULL OR " +
                "(m.price <= 10000 AND :priceRange = 'below_10000') OR " +
                "(m.price > 10000 AND m.price <= 20000 AND :priceRange = '10000_20000') OR " +
                "(m.price > 20000 AND :priceRange = 'above_20000')) " +
                "AND (:isAlone IS NULL OR m.isAlone = :isAlone)")
        List<Menu> findByFilters(@Param("category") String category,
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
        List<Menu> findByFiltersAndSortByLikes(@Param("category") String category,
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
        List<Menu> findAllOrderedByStoreBusyAndCookingTime();
}




