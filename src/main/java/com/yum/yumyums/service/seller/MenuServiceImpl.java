package com.yum.yumyums.service.seller;

import com.yum.yumyums.dto.seller.MenuDTO;
import com.yum.yumyums.entity.review.Review;
import com.yum.yumyums.entity.seller.Menu;
import com.yum.yumyums.enums.Busy;
import com.yum.yumyums.repository.review.ReviewRepository;
import com.yum.yumyums.repository.seller.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;
    private final ReviewRepository reviewRepository;

    public List<MenuDTO> getMenusByStoreId(int storeId) {
        List<MenuDTO> returnDto = new ArrayList<>();
        List<Menu> findAll = menuRepository.findByStoreId(storeId);

        for (Menu findEntity : findAll) {
            returnDto.add(findEntity.entityToDto());
        }

        return returnDto;
    }


    public Optional<MenuDTO> findById(int id) {
        return menuRepository.findById(id)
                .map(Menu::entityToDto);
    }


    //필터 선택후 정렬
    public List<MenuDTO> getMenusByFilters(String category, String priceRange, Boolean isAlone, String sort) {
        List<MenuDTO> returnDto = new ArrayList<>();
        List<Menu> menus = menuRepository.findAllByFilters(category, priceRange, isAlone);

        if ("rating".equals(sort)) {
            menus.sort((m1, m2) -> {
                OptionalDouble avgRating1 = getAverageRateForMenu(m1.getId());
                OptionalDouble avgRating2 = getAverageRateForMenu(m2.getId());
                return Double.compare(avgRating2.orElse(0.0), avgRating1.orElse(0.0));
            });
        } else if ("likes".equals(sort)) {
            menus = menuRepository.findAllByFiltersAndSortByLikes(category, priceRange, isAlone);

        } else if ("busy".equals(sort)) {
            menus = menuRepository.findAllOrderedByStoreBusyAndCookingTime(category, priceRange, isAlone);
        }

        for (Menu menu : menus) {
            returnDto.add(menu.entityToDto());
        }
        return returnDto;
    }


    //리뷰 평균 평점 계산하기
    public OptionalDouble getAverageRateForMenu(int menuId) {
        var reviews = reviewRepository.findByMenuId(menuId);
        return reviews.stream()
                .mapToDouble(Review::getRate)
                .average();
    }

}
