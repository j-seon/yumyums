package com.yum.yumyums.controller;

import com.yum.yumyums.dto.TemplateData;
import com.yum.yumyums.dto.user.MarkStationDTO;
import com.yum.yumyums.dto.user.MemberDTO;
import com.yum.yumyums.service.user.MarkStationService;
import com.yum.yumyums.service.user.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    /*
    회원가입 페이지 - GET : /member
    회원가입 등록 - POST : /member
    회원 상세정보 - GET : /member/id
    회원 상세정보 수정 페이지 - GET : /member/id/update
    회원 상세정보 수정 등록  - PUT : /member/id
    회원 탈퇴  - PUT : /member/id/withdraw
    */

    private final MemberService memberService;
    private final MarkStationService markStationService;

    @GetMapping("")
    public String memberSaveForm(Model model, TemplateData templateData){
        templateData.setViewPath("user/memberSave");
        model.addAttribute("templateData",templateData);
        return "template";
    }

    @PostMapping("")
    public String memberSaveSubmit(MemberDTO memberDTO, MarkStationDTO markStationDTO){
        /*templateData.setViewPath("");*/
        memberService.save(memberDTO, markStationDTO);

        return "redirect:/";

    }

}