


package com.yum.yumyums.controller;


import com.yum.yumyums.dto.TemplateData;
import com.yum.yumyums.dto.seller.StoreDTO;
import com.yum.yumyums.service.seller.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/map")
public class MapController {
    private final StoreService storeService;

    @GetMapping
    public String showOnMap(Model model, TemplateData templateData) {

        templateData.setViewPath("menu/map");
        List<StoreDTO> stores = storeService.getStoresOnMap();
        model.addAttribute("stores", stores);
        return "template";
    }

}


