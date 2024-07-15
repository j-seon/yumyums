package com.yum.yumyums.reference.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/inc/alert")
    public String alert(){
        return "inc/alert";
    }
}
