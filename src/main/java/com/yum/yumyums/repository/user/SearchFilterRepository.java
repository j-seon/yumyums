package com.yum.yumyums.repository.user;

import com.yum.yumyums.entity.user.SearchFilter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchFilterRepository extends JpaRepository<SearchFilter, Integer> {
}
