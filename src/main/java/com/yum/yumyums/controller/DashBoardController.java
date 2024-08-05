package com.yum.yumyums.controller;

import com.yum.yumyums.dto.FaqDTO;
import com.yum.yumyums.dto.TemplateData;
import com.yum.yumyums.dto.orders.OrdersDetailDTO;
import com.yum.yumyums.enums.FoodCategory;
import com.yum.yumyums.enums.PayType;
import com.yum.yumyums.service.DashBoardService;
import com.yum.yumyums.service.FaqService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard")
public class DashBoardController {

    private final DashBoardService dashBoardService;

    @GetMapping("/{storeId}")
    public String showDashboard(Model model, TemplateData templateData,@PathVariable String storeId) {
        HashMap<String, Object> storeInfo = new HashMap<>();
        List<HashMap<String, Object>> ordersHashList = new ArrayList<>();
        int intStoreId = Integer.parseInt(storeId);
        //금일 평점
        String todayRating = dashBoardService.getAverageRateByStoreId(intStoreId);
        //어제자 평점
        String differRate = dashBoardService.getDifferBetweenTodayAndYesterDayRate(intStoreId);
        //금일 매출액
        int todaySaleVolume= dashBoardService.getTodayTotalSalesVolumeByStoreId(intStoreId);
        //금일 주문 건수
        long todayOrderCount= dashBoardService.countOrdersForTodayByStoreId(intStoreId);

        storeInfo.put("todayRating",todayRating);
        storeInfo.put("differRate",differRate);
        storeInfo.put("todaySaleVolume",todaySaleVolume);
        storeInfo.put("todayOrderCount",todayOrderCount);

        ordersHashList = dashBoardService.getOrderHashListByStoreId(intStoreId);

        model.addAttribute("storeInfo", storeInfo);
        model.addAttribute("ordersHashList", ordersHashList);

        templateData.setViewPath("dashboard/index");

        model.addAttribute("templateData", templateData);
        System.out.println(ordersHashList);
        return "dashBoardTemplate";
    }

    @GetMapping("/{storeId}/analysis")
    public String analysisStorePage(Model model, TemplateData templateData,@PathVariable String storeId) {
        int intStoreId = Integer.parseInt(storeId);
        List<OrdersDetailDTO> ordersDetailList = dashBoardService.findAllByStoreId(intStoreId);
        List<Map<String,Object>> findMenuInfoList = dashBoardService.findMenuInfoList(intStoreId);


        for(Map<String, Object> t:findMenuInfoList){
            System.out.println(t.get("category"));
            System.out.println(t.get("price"));
            System.out.println(t.get("name"));
            System.out.println(t.get("cookingTime"));
            System.out.println(t.get("orderCount"));
            System.out.println(t.get("avgRate"));
        }

        model.addAttribute("ordersDetailList", ordersDetailList);
        model.addAttribute("findMenuInfoList", findMenuInfoList);

        templateData.setViewPath("dashboard/analysis");

        model.addAttribute("templateData", templateData);
        return "dashBoardTemplate";
    }
}

