package com.yum.yumyums.service.seller;

import com.yum.yumyums.dto.seller.MenuDTO;
import com.yum.yumyums.enums.FoodCategory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

@Service
public interface MenuService {
    Optional<MenuDTO> findById(int id);
    List<MenuDTO> getMenusByFilters(FoodCategory category, String priceRange, Boolean isAlone, String sort);
    OptionalDouble getAverageRateForMenu(int menuId);
    List<MenuDTO> getMenusByStoreId(int storeId);
    void save(MenuDTO menuDTO);
}
