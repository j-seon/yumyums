package com.yum.yumyums.repository.user;

import com.yum.yumyums.entity.seller.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchRepository extends JpaRepository<Store, Long> {


	// TODO searchStores를 진행할 때, 거리가 맞는 것들만 뽑아서 검색해야 함.
	// TODO Group By를 하든 다른 처리를 하든 해서 중복없애기
	@Query("SELECT s FROM Store s LEFT JOIN Menu m " +
			"ON s.id = m.store.id " +
			"WHERE s.name LIKE %:searchValue% " +
			"OR s.category LIKE %:searchValue% " +
			"OR m.name LIKE %:searchValue%")
	List<Store> findStores(@Param("searchValue") String searchValue);
}