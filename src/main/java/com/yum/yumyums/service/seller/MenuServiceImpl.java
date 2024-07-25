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

    public Optional<MenuDTO> findById(int id) {
        return menuRepository.findById(id)
                .map(Menu::entityToDto);
    }

  /*  @Override
    public List<Menu> getAllActiveMenus() {
        return menuRepository.findByIsActiveTrue();
    } */

    public List<MenuDTO> getMenusByFilters(String category, String priceRange, Boolean isAlone, String sort) {
        List<MenuDTO> returnDto = new ArrayList<>();
        List<Menu> menus = menuRepository.findByFilters(category, priceRange, isAlone);

        if ("rating".equals(sort)) {
            menus.sort((m1, m2) -> {
                OptionalDouble avgRating1 = getAverageRateForMenu(m1.getId());
                OptionalDouble avgRating2 = getAverageRateForMenu(m2.getId());
                return Double.compare(avgRating2.orElse(0.0), avgRating1.orElse(0.0));
            });
        } else if ("likes".equals(sort)) {
            menus = menuRepository.findByFiltersAndSortByLikes(category, priceRange, isAlone);

        } else if ("busy".equals(sort)) {
            Map<Busy, Integer> busyOrder = Map.of(
                    Busy.SPACIOUS, 1,
                    Busy.NOMAL, 2,
                    Busy.CROWDED, 3,
                    Busy.FULL, 4
            );
            menus.sort((m1, m2) -> {
                int busyComparison = Integer.compare(
                        busyOrder.get(m1.getStore().getBusy()),
                        busyOrder.get(m2.getStore().getBusy())
                );

                if (busyComparison != 0) {
                    return busyComparison;
                }

                return Integer.compare(m1.getCookingTime(), m2.getCookingTime());
            });
        }

        for (Menu menu : menus) {
            returnDto.add(menu.entityToDto());
        }
        return returnDto;
    }


    public OptionalDouble getAverageRateForMenu(int menuId) {
        var reviews = reviewRepository.findByMenuId(menuId);
        return reviews.stream()
                .mapToDouble(Review::getRate)
                .average();
    }

}
