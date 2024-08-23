package com.yum.yumyums.controller;

import com.yum.yumyums.dto.TemplateData;
import com.yum.yumyums.dto.orders.OrdersDetailDTO;
import com.yum.yumyums.dto.seller.StoreDTO;
import com.yum.yumyums.enums.Busy;
import com.yum.yumyums.service.DashBoardService;
import com.yum.yumyums.service.ImagesService;
import com.yum.yumyums.service.seller.StoreService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard")
public class DashBoardController {

    private final DashBoardService dashBoardService;
    private final StoreService storeService;
    private final ImagesService imagesService;

    @GetMapping("/{storeIdx}")
    public String showDashboard(Model model, TemplateData templateData,@PathVariable String storeIdx, HttpServletRequest request) {
        HashMap<String, Object> storeInfo = new HashMap<>();
        List<HashMap<String, Object>> ordersHashList = new ArrayList<>();

        HttpSession session = request.getSession();

        Integer sessionStoreId = (Integer) session.getAttribute("storeId");

        if(sessionStoreId == null){
            templateData.setMessage("매장 로그인이 필요한 서비스입니다.");
            templateData.setUrl("/stores");
            model.addAttribute("templateData", templateData);
            return "inc/alert";
        }else{
            int intStoreId = sessionStoreId;
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
            storeInfo.put("storeBusy",storeService.findById(Integer.parseInt(storeIdx)).getBusy());

            ordersHashList = dashBoardService.getOrderHashListByStoreId(intStoreId);
            model.addAttribute("storeInfo", storeInfo);
            model.addAttribute("storeId", storeIdx);
            model.addAttribute("ordersHashList", ordersHashList);

            templateData.setViewPath("dashboard/index");

            model.addAttribute("templateData", templateData);
        }
        return "dashBoardTemplate";
    }

    @GetMapping("/{storeId}/analysis")
    public String analysisStorePage(Model model, TemplateData templateData,@PathVariable String storeId,HttpServletRequest request) {

        HttpSession session = request.getSession();

        Integer sessionStoreId = (Integer) session.getAttribute("storeId");

        if(sessionStoreId == null){
            templateData.setMessage("매장 로그인이 필요한 서비스입니다.");
            templateData.setUrl("/stores");
            model.addAttribute("templateData", templateData);
            return "inc/alert";
        }
        int intStoreId = sessionStoreId;
        List<OrdersDetailDTO> ordersDetailList = dashBoardService.findAllByStoreId(intStoreId);
        List<Map<String,Object>> findMenuInfoList = dashBoardService.findMenuInfoList(intStoreId);

        model.addAttribute("ordersDetailList", ordersDetailList);
        model.addAttribute("findMenuInfoList", findMenuInfoList);

        templateData.setViewPath("dashboard/analysis");

        model.addAttribute("templateData", templateData);
        return "dashBoardTemplate";
    }

    @PutMapping("")
    @ResponseBody
    public String updateBusy(@RequestParam("busy") String busy, @RequestParam("storeId") int storeId) {
        try {
            StoreDTO storeDTO = storeService.findById(storeId);
            storeDTO.setBusy(Busy.valueOf(busy));
            storeService.update(storeDTO.dtoToEntity());
            return  "success";
        }catch (Exception e){
            return  "fail";
        }
    }

    @GetMapping("/out")
    public String dashboardOut(Model model, TemplateData templateData, HttpServletRequest request){
        HttpSession session = request.getSession();
        session.removeAttribute("storeId");
        return "redirect:/stores";
    }
}

