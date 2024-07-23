package com.yum.yumyums.service.user;

import com.yum.yumyums.entity.seller.Menu;
import com.yum.yumyums.entity.seller.Store;
import com.yum.yumyums.repository.user.SearchRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class  SearchServiceImpl implements SearchService {

	private final SearchRepository searchRepository;

	public SearchServiceImpl(SearchRepository searchRepository) {
		this.searchRepository = searchRepository;
	}

	@Override
	public List<Menu> findMenus(String searchValue) {
		//TODO Entity -> DTO 변환작업 필요
		//TODO 추가 작업이 있다면 시행할 것
		return searchRepository.findByNameLike(searchValue);
	}

	@Override
	public List<Store> findStores(String searchValue) {
		//TODO Entity -> DTO 변환작업이 필요함
		return searchRepository.findStores(searchValue);
	}
}
