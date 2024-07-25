package com.yum.yumyums.repository;

import com.yum.yumyums.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StationRepository extends JpaRepository<Station, String> {
    List<Station> findByNameContaining(String keyword);
}
