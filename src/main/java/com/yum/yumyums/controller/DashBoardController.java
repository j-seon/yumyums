package com.yum.yumyums.controller;

import com.yum.yumyums.dto.FaqDTO;
import com.yum.yumyums.dto.TemplateData;
import com.yum.yumyums.service.DashBoardService;
import com.yum.yumyums.service.FaqService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard")
public class DashBoardController {

    private final DashBoardService dashBoardService;

    @GetMapping("/{storeId}")
    public String faqList(Model model, TemplateData templateData,@PathVariable String storeId) {
        int intStoreId = Integer.parseInt(storeId);

        //금일 평점
        double todayRating = dashBoardService.getAverageRateByStoreId(intStoreId);
        //어제자 평점
        double beforeTodayRating = dashBoardService.getAverageRateBeforeTodayByStoreId(intStoreId);
        //금일 매출액
        int todaySaleVolume= dashBoardService.getTodayTotalSalesVolumeByStoreId(intStoreId);
        System.out.println("todayRating : "+todayRating);
        System.out.println("beforeTodayRating : "+beforeTodayRating);
        templateData.setViewPath("dashboard/index");

        model.addAttribute("templateData", templateData);

        return "dashBoardTemplate";
    }
}

