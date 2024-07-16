package com.yum.yumyums.controller;

import com.yum.yumyums.dto.FaqDTO;
import com.yum.yumyums.dto.TemplateData;
import com.yum.yumyums.service.FaqService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/faq")
public class FaqController {

    private final FaqService faqService;

    @GetMapping("")
    public String faqList(Model model, TemplateData templateData) {
        templateData.setViewPath("faq/list");
        List<String> categories = faqService.findDistinctCategories();

        model.addAttribute("categories", categories);
        model.addAttribute("templateData", templateData);

        return "template";
    }

    @GetMapping("/{category}")
    @ResponseBody
    public List<FaqDTO> searchFAQ(@PathVariable String category) {
        List<FaqDTO> faqList = faqService.findByCategory(category);
        return faqList;
    }


}

