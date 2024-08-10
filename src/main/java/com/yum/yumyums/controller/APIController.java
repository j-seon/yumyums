package com.yum.yumyums.controller;


import com.yum.yumyums.dto.StationDTO;
import com.yum.yumyums.dto.TemplateData;
import com.yum.yumyums.dto.user.MemberDTO;
import com.yum.yumyums.service.DashBoardService;
import com.yum.yumyums.service.StationService;
import com.yum.yumyums.dto.seller.StoreDTO;
import com.yum.yumyums.service.chat.ChatMemberService;
import com.yum.yumyums.service.seller.StoreService;
import com.yum.yumyums.service.user.SearchService;
import com.yum.yumyums.util.SessionUtil;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class APIController {
    private final StationService stationService;
    private final SearchService searchService;
    private final StoreService storeService;
    private final DashBoardService dashBoardService;
    private final ChatMemberService chatMemberService;

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
    public ResponseEntity<Boolean> duplicate(@RequestParam("keyword") String keyword, @RequestParam("field") String field){
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

    @GetMapping("/dailySums")
    @ResponseBody
    public List<Map<String,Object>> dailySums(@RequestParam("storeId") String storeId) {
        int intStoreId = Integer.parseInt(storeId);
        return dashBoardService.findDailySumsByStoreId(intStoreId);
    }

    @GetMapping("/monthlySums")
    @ResponseBody
    public List<Map<String,Object>> monthlySums(@RequestParam("storeId") String storeId) {
        int intStoreId = Integer.parseInt(storeId);
        return dashBoardService.findMonthlySumsByStoreId(intStoreId);
    }
    @GetMapping("/yearlySums")
    @ResponseBody
    public List<Map<String,Object>> yearlySums(@RequestParam("storeId") String storeId) {
        int intStoreId = Integer.parseInt(storeId);
        return dashBoardService.findYearlySumsByStoreId(intStoreId);
    }
    @GetMapping("/menuInfo")
    @ResponseBody
    public List<Map<String,Object>> menuInfo(@RequestParam("storeId") String storeId) {
        int intStoreId = Integer.parseInt(storeId);

        return dashBoardService.findMenuInfoList(intStoreId);
    }
    @GetMapping("/isLogin")
    @ResponseBody
    public HashMap<String, Object> isLogin(HttpSession session) {
        SessionUtil sessionUtil = new SessionUtil();
        HashMap<String, Object> returnHashMap=new HashMap<>();

        if(SessionUtil.isLoginAsMember(session)){
            MemberDTO memberDTO= (MemberDTO) session.getAttribute("loginUser");
            List<HashMap<String, Object>> chatRoomHashList =  chatMemberService.findChatRoomInfoByMemberId(memberDTO.getMemberId());
            returnHashMap.put("chatRoomHashList",chatRoomHashList);
            returnHashMap.put("loginUser",memberDTO.getMemberId());
            return returnHashMap;
        }else{
            return null;
        }
    }
}
