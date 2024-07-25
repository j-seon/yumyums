package com.yum.yumyums.service.user;

import com.yum.yumyums.dto.seller.MenuDTO;
import com.yum.yumyums.dto.seller.StoreDTO;
import com.yum.yumyums.entity.seller.Menu;
import com.yum.yumyums.entity.seller.Store;

import java.util.List;

public interface SearchService {

	public List<MenuDTO> findMenus(String searchValue);

	public List<StoreDTO> findStores(String searchValue);
}
