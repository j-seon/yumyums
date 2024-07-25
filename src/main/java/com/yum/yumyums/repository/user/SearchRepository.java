package com.yum.yumyums.repository.user;

import com.yum.yumyums.entity.seller.Menu;
import com.yum.yumyums.entity.seller.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchRepository extends JpaRepository<Store, Long> {


	// TODO searchStores를 진행할 때, 거리가 맞는 것들만 뽑아서 검색해야 함.
	@Query("SELECT s FROM Store s " +
			"LEFT JOIN Menu m ON s.id = m.store.id " +
			"LEFT JOIN Seller sl On s.seller.id = sl.id " +
			"WHERE s.name LIKE %:searchValue% " +
			"OR s.category LIKE %:searchValue% " +
			"OR m.name LIKE %:searchValue%")
	List<Store> findAllStoreByKeyword(@Param("searchValue") String searchValue);

	// SELECT m FROM Menu m WHERE m.name LIKE %:searchValue%
	List<Menu> findAllMenuByNameLike(String searchValue);
}