package com.yum.yumyums.controller;

import com.yum.yumyums.dto.TemplateData;
import com.yum.yumyums.util.SessionUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
    public String join(Model model, TemplateData templateData, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(SessionUtil.isLogin(session)){
            return "redirect:/"; // Referer가 없을 경우 기본 페이지로 리다이렉트
        }
        templateData.setViewPath("user/join");
        return "template";
    }
    @GetMapping("/login")
    public String login(Model model, TemplateData templateData, HttpServletRequest request){
        HttpSession session = request.getSession();
        if(SessionUtil.isLogin(session)){
            return "redirect:/"; // Referer가 없을 경우 기본 페이지로 리다이렉트
        }
        templateData.setViewPath("user/login");
        return "template";
    }
    @GetMapping("logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/inc/alert")
    public String alert(){
        return "inc/alert";
    }

}
