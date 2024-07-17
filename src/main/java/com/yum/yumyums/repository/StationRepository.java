package com.yum.yumyums.repository;

import com.yum.yumyums.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRepository extends JpaRepository<Station, String> {
}
