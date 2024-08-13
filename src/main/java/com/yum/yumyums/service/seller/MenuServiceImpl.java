package com.yum.yumyums.service.seller;

import com.yum.yumyums.dto.seller.MenuDTO;
import com.yum.yumyums.entity.Images;
import com.yum.yumyums.entity.review.Review;
import com.yum.yumyums.entity.seller.Menu;
import com.yum.yumyums.enums.FoodCategory;
import com.yum.yumyums.repository.orders.OrdersDetailRepository;
import com.yum.yumyums.repository.review.ReviewRepository;
import com.yum.yumyums.repository.seller.MenuRepository;
import com.yum.yumyums.service.ImagesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;
    private final ReviewRepository reviewRepository;
    private final ImagesService imagesService;
    private final OrdersDetailRepository ordersDetailRepository;

    @Override
    public Optional<MenuDTO> findById(int id) {
        return menuRepository.findById(id)
                .map(Menu::entityToDto);
    }

    @Override
    public List<MenuDTO> getMenusByFilters(List<FoodCategory> categories, List<String> priceRanges, Boolean isAlone, String sort) {
        List<MenuDTO> returnDto = new ArrayList<>();
        List<Menu> menus = menuRepository.findAllByFilters(categories, priceRanges, isAlone);

        if ("rating".equals(sort)) {
            menus.sort((m1, m2) -> {
                double avgRating1 = getAverageRateForMenu(m1.getId());
                double avgRating2 = getAverageRateForMenu(m2.getId());
                return Double.compare(avgRating2, avgRating1);
            });
        } else if ("likes".equals(sort)) {
            menus = menuRepository.findAllByFiltersAndSortByLikes(categories, priceRanges, isAlone);
        } else if ("busy".equals(sort)) {
            menus = menuRepository.findAllOrderedByStoreBusyAndCookingTime(categories, priceRanges, isAlone);
        } else if ("price".equals(sort)) {
            menus = menuRepository.findAllOrderedByPrice(categories, priceRanges, isAlone);
        } else if ("orderCount".equals(sort)) {
            menus.sort((m1, m2) -> {
                int count1 = getMenuOrderCount(m1.getId());
                int count2 = getMenuOrderCount(m2.getId());
                return Integer.compare(count2, count1);
            });
        }

        for (Menu menu : menus) {
            returnDto.add(menu.entityToDto());
        }
        return returnDto;
    }

    @Override
    public double getAverageRateForMenu(int menuId) {
        var reviews = reviewRepository.findByMenuId(menuId);
        OptionalDouble averageRate = reviews.stream()
                .mapToDouble(Review::getRate)
                .average();

        return averageRate.isPresent() ? Math.round(averageRate.getAsDouble() * 10) / 10.0 : 0.0;
    }

    @Override
    public int getMenuOrderCount(int menuId) {
        Integer orderCount = ordersDetailRepository.findTotalOrdersByMenuId(menuId);
        return (orderCount != null) ? orderCount : 0;
    }

    @Override
    public List<MenuDTO> getMenusByStoreId(int storeId) {
        List<Menu> menus = menuRepository.findMenusByStoreIdOrderedByIdDesc(storeId);
        List<MenuDTO> menuDTOs = menus.stream()
                .map(menu -> menu.entityToDto())
                .collect(Collectors.toList());
        return menuDTOs;
    }

    @Override
    public void save(MenuDTO menuDTO) {
        Images savedImages = imagesService.saveImage(menuDTO.getImagesDTO());
        Menu menu = menuDTO.dtoToEntity();
        menu.setImages(savedImages);
        menuRepository.save(menu);
    }

}