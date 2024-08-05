package com.yum.yumyums.service;

public interface EmailService {
    String createEmailCode();

    void sendEmail(String email, String emailCode);

    void holdEmailCode(String email, String emailCode);

    boolean isEmailCodeValid(String email, String emailCode);

    void removeCodeHolder(String email);
}
