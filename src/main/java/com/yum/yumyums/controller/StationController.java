package com.yum.yumyums.controller;

import com.yum.yumyums.dto.StationDTO;
import com.yum.yumyums.service.StationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/stations")
public class StationController {
    private final StationService stationService;

    @GetMapping("")
    public String stationApi() {
        stationService.getAndSaveStations();
        return "redirect:/";
    }
}
