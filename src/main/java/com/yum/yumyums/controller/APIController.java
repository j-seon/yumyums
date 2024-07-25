package com.yum.yumyums.controller;


import com.yum.yumyums.dto.StationDTO;
import com.yum.yumyums.service.StationService;
import com.yum.yumyums.dto.seller.StoreDTO;
import com.yum.yumyums.service.user.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class APIController {
    private final StationService stationService;
    private final SearchService searchService;

    @GetMapping("/stations")
    @ResponseBody
    public List<StationDTO> searchStations(@RequestParam("keyword") String keyword) {
        return stationService.searchStations(keyword);
    }

    @GetMapping("/stores")
    public List<StoreDTO> findStores(@RequestParam("searchValue") String searchValue) {
      System.out.println("searchValue: " + searchValue);
      if(searchValue == null) {
        searchValue = ""; //전체목록 가져오기
      }
      return searchService.findStores(searchValue);
    }

}
