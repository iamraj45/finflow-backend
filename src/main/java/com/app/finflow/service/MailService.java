package com.app.finflow.service;

public interface MailService {

    void sendResetPasswordEmail(String toEmail, String resetLink);
}
