package com.yum.yumyums.controller;

import com.yum.yumyums.board.dto.BoardDTO;
import com.yum.yumyums.dto.FaqDTO;
import com.yum.yumyums.dto.TemplateData;

import com.yum.yumyums.service.FaqService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/faq")
public class FaqController {
    @Autowired
    @Qualifier("faqService")
    private FaqService faqService;

    /*
    글 목록 - GET : /board
    새 글 등록 페이지 - GET : /board/save
    새 글 등록  - POST : /board
    1글 보기 - GET : /board/1
    1번 글 수정 페이지 - GET : /board/1/update
    1번 글 수정등록 - PUT : /board/1
    1번 글 삭제 - DELETE : /board/1
    */


    /*
    글 목록 진입
    * */
    @GetMapping("")
    public String boardList(Model model, TemplateData templateData){
        templateData.setViewPath("faq/list");
        List<FaqDTO> faqList = faqService.findAll();

        model.addAttribute("faqList", faqList);
        model.addAttribute("templateData",templateData);

        return "template";
    }








}
