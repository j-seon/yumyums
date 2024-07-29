package com.yum.yumyums.controller;


import com.yum.yumyums.dto.StationDTO;
import com.yum.yumyums.service.StationService;
import com.yum.yumyums.dto.seller.StoreDTO;
import com.yum.yumyums.service.seller.StoreService;
import com.yum.yumyums.service.user.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class APIController {
    private final StationService stationService;
    private final SearchService searchService;
    private final StoreService storeService;

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


    @GetMapping("/duplicate")
    @ResponseBody
    public ResponseEntity<Boolean> nameChk(@RequestParam("keyword") String keyword, @RequestParam("field") String field){
        // 입력값 확인
        if (keyword == null || keyword.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(false); // 잘못된 요청 처리
        }

        System.out.println("Checking keyword: " + keyword);
        boolean exists ;
        // null인 경우 false, 존재하는 경우 true 반환
        switch(field){
            case("storeName"):
                exists = storeService.findByName(keyword) != null;
                return ResponseEntity.ok(exists);
            case("member"):
                break;
            case("seller"):
                break;
            case("menu"):
                break;
        }

        return null;
    }

}
