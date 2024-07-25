package com.yum.yumyums.controller;

import com.yum.yumyums.dto.TemplateData;
import com.yum.yumyums.dto.seller.MenuDTO;
import com.yum.yumyums.service.seller.MenuService;
import com.yum.yumyums.service.seller.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/menu")
public class RecommendController {
    private final MenuService menuService;
    private final StoreService storeService;

    /*@GetMapping
    public String getAllMenus(Model model,TemplateData templateData) {
        templateData.setViewPath("menu/list");
        List<Menu> menus = menuService.getAllActiveMenus();
        model.addAttribute("menus", menus);
        model.addAttribute("templateData", templateData);
        return "template";
    }*/

    @GetMapping
    public String getMenusByFilters(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String priceRange,
            @RequestParam(required = false) Boolean isAlone,
            @RequestParam(required = false) String sort,
            Model model, TemplateData templateData) {

        templateData.setViewPath("menu/list");
        List<MenuDTO> menus = menuService.getMenusByFilters(category, priceRange, isAlone, sort);
        Map<Integer, Integer> likeCounts = new HashMap<>();
        Map<Integer, Double> averageRatings = new HashMap<>();

        for (MenuDTO menu : menus) {
            int storeId = menu.getStoreDTO().getStoreId();
            int likeCount = storeService.getLikesForStore(storeId);
            double averageRating = menuService.getAverageRateForMenu(menu.getId()).orElse(0.0);

            likeCounts.put(storeId, likeCount);
            averageRatings.put(menu.getId(), averageRating);
        }

        model.addAttribute("menus", menus);
        model.addAttribute("likeCounts", likeCounts);
        model.addAttribute("averageRatings", averageRatings);
        model.addAttribute("templateData", templateData);

        return "template";
    }



    @GetMapping("/{id}")
    public String getMenu(@PathVariable("id") int id, Model model, TemplateData templateData) {

        templateData.setViewPath("menu/detail");
        Optional<MenuDTO> menu = menuService.findById(id);

        if (menu.isPresent()) {
            model.addAttribute("menu", menu.get());

            var averageRating = menuService.getAverageRateForMenu(id);
            model.addAttribute("templateData", templateData);
            model.addAttribute("averageRate", averageRating.isPresent() ? averageRating.getAsDouble() : "등록된 리뷰 없음");

            int storeId = menu.get().getStoreDTO().getStoreId();
            int likeCount = storeService.getLikesForStore(storeId);
            model.addAttribute("likeCount", likeCount);

            return "template";

        } else {

            return "index";
        }
    }
}