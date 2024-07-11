package com.yum.yumyums.service.user;

import com.yum.yumyums.entity.seller.Store;

import java.util.List;

public interface SearchService {

	public List<Store> searchAll();

	public List<Store> findStores(String searchValue);
}
