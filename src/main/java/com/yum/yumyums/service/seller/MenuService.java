package com.yum.yumyums.service.seller;

import com.yum.yumyums.dto.seller.MenuDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

@Service
public interface MenuService {
    //List<Menu> getAllActiveMenus();
    Optional<MenuDTO> findById(int id);
    List<MenuDTO> getMenusByFilters(String category, String priceRange, Boolean isAlone, String sort);
    OptionalDouble getAverageRateForMenu(int menuId);

}
