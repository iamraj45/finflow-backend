package com.app.finflow.service;

import jakarta.mail.MessagingException;

public interface MailService {

    void sendResetPasswordEmail(String toEmail, String resetLink) throws MessagingException;
    void sendVerificationEmail(String to, String verificationLink) throws MessagingException;
}
