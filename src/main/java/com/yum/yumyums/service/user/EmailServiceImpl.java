package com.yum.yumyums.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {


    private final JavaMailSender mailSender;
    private final Map<String, String> cashedCodeStorage = new HashMap<>();


    @Override
    public String createEmailCode() {
        //인증번호 생성 
        Random random = new Random();
        // 테스트를 위해 배포 전까지 123456 으로 임의설정
        // return String.format("%06d", random.nextInt(900000) + 100000);
        return "123456";
    }

    @Override
    public void sendEmail(String email, String emailCode) {
        //이메일 전송
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("YUMYUMS 회원가입 이메일 인증코드");
        message.setText("인증코드 : " + emailCode);
        mailSender.send(message);
    }

    @Override
    public void cacheEmailCode(String email, String emailCode) {
        //이메일과 인증번호를 메모리 내 Map에 저장
        cashedCodeStorage.put(email, emailCode);
    }

    @Override
    public boolean isEmailCodeValid(String email, String emailCode) {
        // Map에 저장된 인증번호와 비교
        String cachedCode = cashedCodeStorage.get(email);
        return cachedCode!=null && cachedCode.equals(emailCode) ;
    }

    @Override
    public void removeCodeHolder(String email) {
        // 번호 인증 후 Map에서 삭제
        cashedCodeStorage.remove(email);
    }
}
