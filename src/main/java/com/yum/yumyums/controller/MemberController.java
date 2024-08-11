package com.yum.yumyums.controller;

import com.yum.yumyums.dto.ImagesDTO;
import com.yum.yumyums.dto.TemplateData;
import com.yum.yumyums.dto.user.MarkStationDTO;
import com.yum.yumyums.dto.user.MemberDTO;
import com.yum.yumyums.dto.user.MemberJoinRequest;
import com.yum.yumyums.service.ImagesService;
import com.yum.yumyums.service.user.MarkStationService;
import com.yum.yumyums.service.user.MemberService;
import com.yum.yumyums.util.ImageDefaultUrl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController extends ImageDefaultUrl {
    /*
    회원가입 페이지 - GET : /member
    회원가입 등록 - POST : /member
    회원 상세정보 - GET : /member/id
    회원 상세정보 수정 페이지 - GET : /member/id/update
    회원 상세정보 수정 등록  - PUT : /member/id
    회원 탈퇴  - DELETE : /member/id
    */

    private final MemberService memberService;
    private final MarkStationService markStationService;
    private final ImagesService imagesService;

    @GetMapping("")
    public String memberSaveForm(Model model, TemplateData templateData){
        templateData.setViewPath("user/memberSave");
        model.addAttribute("templateData",templateData);
        return "template";
    }

    @PostMapping("")
    public String memberSaveSubmit(MemberJoinRequest memberJoinRequest, @RequestParam("memberImg") MultipartFile imgFile){
        System.out.println("request : "+memberJoinRequest);
        MemberDTO memberDTO = memberJoinRequest.getMemberDTO();
        /*MemberDTO memberDTO = memberJoinRequest.getMemberDTO();
        System.out.println("member : "+ memberDTO);

        List<MarkStationDTO> markStationDTOs = memberJoinRequest.getMarkStationDTOs();
        for(MarkStationDTO station : markStationDTOs){
            station.setMemberId(memberDTO.getMemberId());
            System.out.println("station : "+station);
        }*/

        if(!imgFile.isEmpty()){
            imgUrl = "member/"+memberDTO.getMemberId()+"/"+imgFile.getOriginalFilename();
        }
        System.out.println("imgUrl : "+imgUrl);

        String savedImgUrl = imagesService.uploadImage(imgFile, imgUrl);
        System.out.println("savedImgUrl : "+savedImgUrl);
        ImagesDTO imagesDTO = new ImagesDTO();
        imagesDTO.setImgUrl(savedImgUrl);
        memberDTO.setImagesDTO(imagesDTO);
        memberJoinRequest.setMemberDTO(memberDTO);

        memberService.save(memberJoinRequest);

        return "redirect:/";

    }

    @PostMapping("/login")
    public String memberLogin(@RequestParam("redirect") String redirectUrl, MemberDTO memberDTO,HttpServletRequest request, Model model, TemplateData templateData){
        HttpSession session = request.getSession();
        String memberId = memberDTO.getMemberId();
        String memberPw = memberDTO.getPassword();
        if(redirectUrl.isEmpty()){
            redirectUrl = "/";
        }
        System.out.println("redirectUrl :" + redirectUrl);
        MemberDTO member = memberService.findById(memberId);
        if(member != null && member.getPassword().equals(memberPw)){
            session.setAttribute("loginUser",member);
            session.setAttribute("loginType", "m");
            return "redirect:" + redirectUrl;
        }else{
            templateData.setMessage("아이디 또는 비밀번호가 일치하지 않습니다.");
            templateData.setUrl("/login?redirect=" + redirectUrl);
            model.addAttribute("templateData", templateData);
            return "/inc/alert";
        }

    }

}
