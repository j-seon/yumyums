package com.yum.yumyums.service.user;

import com.yum.yumyums.dto.seller.MenuDTO;
import com.yum.yumyums.dto.seller.StoreDTO;
import com.yum.yumyums.entity.seller.Menu;
import com.yum.yumyums.entity.seller.Store;
import com.yum.yumyums.repository.user.SearchRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class  SearchServiceImpl implements SearchService {

	private final SearchRepository searchRepository;

	public SearchServiceImpl(SearchRepository searchRepository) {
		this.searchRepository = searchRepository;
	}

	@Override
	public List<MenuDTO> findMenus(String searchValue) {
		List<Menu> menus = searchRepository.findAllMenuByNameLike(searchValue);
		List<MenuDTO> menuDTOs = new ArrayList<>();
		for (Menu menu : menus) {
			//TODO Menu Entity에 entityToDto를 만들 것.
		}
		return menuDTOs;
	}

	@Override
	public List<StoreDTO> findStores(String searchValue) {
		List<Store> stores = searchRepository.findAllStoreByKeyword(searchValue);
		List<StoreDTO> storeDTOs = new ArrayList<>();
		for (Store store : stores) {
			storeDTOs.add(store.entityToDto());
		}
		return storeDTOs;
	}
}
