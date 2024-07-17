package com.yum.yumyums.controller;

import com.yum.yumyums.dto.TemplateData;
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
    public String join(Model model, TemplateData templateData) {
        templateData.setViewPath("user/join");
        return "template";
    }
    @GetMapping("/login")
    public String login(Model model, TemplateData templateData){
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

    @GetMapping("/stationUpdate")
    public String stataionUpdate(){

        return "redirect:/";
    }

/*    @GetMapping("/s")
    public String sshowChatPage(Model model, TemplateData templateData) {
        templateData.setViewPath("chat/list");

        String accessKey = "2b0f47ca-9506-4b14-8a1c-7c6245ad3cde"; // 발급받은 accessKey

        try {
            WebClient webClient = WebClient.create("https://t-data.seoul.go.kr/apig/apiman-gateway/tapi/TaimsKsccDvSubwayStationGeom/1.0");
            String response = webClient.get()
                    .uri(uriBuilder -> uriBuilder.queryParam("apikey", accessKey).build())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            System.out.println(response);
        } catch (Exception e) {
            System.out.println(e);
        }

        return "template";
    }*/

}
