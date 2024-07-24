package com.yum.yumyums.controller;

import com.yum.yumyums.service.user.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<Void> sendEmail(@RequestParam("email") String email){
        try {
            String emailCode = emailService.createEmailCode();
            emailService.sendEmail(email, emailCode);
            emailService.cacheEmailCode(email, emailCode);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @PostMapping("/check")
    public ResponseEntity<Void> checkEmail(@RequestParam("email") String email, @RequestParam("emailCode") String emailCode){
        System.out.println(email +" : "+emailCode);
        try {
            if(emailService.isEmailCodeValid(email, emailCode)){
                emailService.removeCodeHolder(email);
                return ResponseEntity.ok().build();
            }else{
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
}
