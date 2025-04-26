package com.app.finflow.service;

import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface MailService {

    void sendResetPasswordEmail(String toEmail, String resetLink) throws MessagingException, UnsupportedEncodingException;
    void sendVerificationEmail(String to, String verificationLink) throws MessagingException, UnsupportedEncodingException;
}
