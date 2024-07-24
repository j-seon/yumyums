package com.yum.yumyums.controller;

import com.yum.yumyums.dto.StationDTO;
import com.yum.yumyums.service.StationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class APIController {
    private final StationService stationService;

    @GetMapping("/stations")
    @ResponseBody
    public List<StationDTO> searchStations(@RequestParam("keyword") String keyword) {
        return stationService.searchStations(keyword);
    }
}
