package com.yum.yumyums.service.user;

import com.yum.yumyums.entity.seller.Store;
import com.yum.yumyums.repository.user.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {
	@Autowired
	private SearchRepository searchRepository;

	public void searchAll(String location, String search) {
		// TODO 전체 목록을 검색하는 조건을 생성
		// return 값을 List<Menu>로 변경할 것
	}

	public List<Store> findStores(String searchValue) {
		return searchRepository.findStores(searchValue);
	}
}
