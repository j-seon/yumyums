package com.yum.yumyums.service.seller;

import com.yum.yumyums.dto.seller.MenuDTO;
import com.yum.yumyums.entity.Images;
import com.yum.yumyums.entity.review.Review;
import com.yum.yumyums.entity.seller.Menu;
import com.yum.yumyums.enums.Busy;
import com.yum.yumyums.repository.review.ReviewRepository;
import com.yum.yumyums.repository.seller.MenuRepository;
import com.yum.yumyums.service.ImagesService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;
    private final ReviewRepository reviewRepository;
    private final ImagesService imagesService;

    public Optional<MenuDTO> findById(int id) {
        return menuRepository.findById(id)
                .map(Menu::entityToDto);
    }

    /*  @Override
    public List<Menu> getAllActiveMenus() {
        return menuRepository.findByIsActiveTrue();
    } */


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
