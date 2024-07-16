package com.yum.yumyums.service.user;

import com.yum.yumyums.entity.seller.Menu;
import com.yum.yumyums.entity.seller.Store;

import java.util.List;

public interface SearchService {

	public List<Menu> findMenus(String searchValue);

	public List<Store> findStores(String searchValue);
}
