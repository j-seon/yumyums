package com.yum.yumyums.controller;

import com.yum.yumyums.service.StationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/station")
public class StationController {
    private final StationService stationService;

    @GetMapping("/api")
    public String stationApi() {
        stationService.getAndSaveStations();
        return "redirect:/";
    }
}
