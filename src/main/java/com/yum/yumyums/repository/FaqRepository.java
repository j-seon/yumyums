package com.yum.yumyums.repository;

import com.yum.yumyums.entity.Faq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FaqRepository extends JpaRepository<Faq, Integer> {
    //카테고리 종류 추출
    @Query("SELECT DISTINCT category FROM Faq ")
    List<String> findDistinctCategories();

    //카테고리 별로 검색
    List<Faq> findByCategory(@Param("category") String category);
}
