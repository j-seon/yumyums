package com.yum.yumyums.controller;

import com.yum.yumyums.dto.TemplateData;
import com.yum.yumyums.dto.seller.SellerDTO;
import com.yum.yumyums.service.seller.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/seller")
public class SellerController {
     /*
    판매자가입 페이지 - GET : /seller
    판매자가입 등록 - POST : /seller
    판매자 상세정보 - GET : /seller/id
    판매자 상세정보 수정 페이지 - GET : /seller/id/update
    판매자 상세정보 수정 등록  - PUT : /seller/id
    판매자 탈퇴  - PUT : /seller/id/withdraw
    */

    private final SellerService sellerService;

    @GetMapping("")
    public String sellerSaveForm(Model model, TemplateData templateData){
        templateData.setViewPath("user/sellerSave");
        model.addAttribute("templateData",templateData);
        return "template";
    }

    @PostMapping("")
    public String sellerSaveSubmit(SellerDTO sellerDTO){
        sellerService.save(sellerDTO);
        return "redirect:/";
    }
}
