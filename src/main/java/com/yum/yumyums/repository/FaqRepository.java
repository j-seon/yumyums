package com.yum.yumyums.repository;

import com.yum.yumyums.entity.Faq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FaqRepository extends JpaRepository<Faq, Integer> {
    @Query("SELECT DISTINCT category FROM Faq ")
    List<String> findDistinctCategories();

//    List<Faq> findByCategory(String category);
    @Query("SELECT f FROM Faq f WHERE f.category = :category")
    List<Faq> findByCategory(@Param("category") String category);
}
