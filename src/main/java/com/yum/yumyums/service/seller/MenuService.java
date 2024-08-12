package com.yum.yumyums.service.seller;

import com.yum.yumyums.dto.seller.MenuDTO;
import com.yum.yumyums.enums.FoodCategory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface MenuService {
    Optional<MenuDTO> findById(int id);
    List<MenuDTO> getMenusByFilters(List<FoodCategory> categories, List<String> priceRanges, Boolean isAlone, String sort);
    double getAverageRateForMenu(int menuId);
    int getMenuOrderCount(int menuId);
    List<MenuDTO> getMenusByStoreId(int storeId);
    void save(MenuDTO menuDTO);
}
