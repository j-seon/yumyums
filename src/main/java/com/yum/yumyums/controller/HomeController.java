
package com.yum.yumyums.controller;


import com.yum.yumyums.dto.TemplateData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("join")
    public String join(Model model, TemplateData templateData) {
        templateData.setViewPath("user/join");
        return "template";
    }

    @GetMapping("/inc/alert")
    public String alert(){
        return "inc/alert";
    }
}
