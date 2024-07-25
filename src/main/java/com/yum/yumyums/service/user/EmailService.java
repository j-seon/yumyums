package com.yum.yumyums.service.user;

public interface EmailService {
    String createEmailCode();

    void sendEmail(String email, String emailCode);

    void cacheEmailCode(String email, String emailCode);

    boolean isEmailCodeValid(String email, String emailCode);

    void removeCodeHolder(String email);
}
