package com.app.finflow.serviceImpl;

import com.app.finflow.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendResetPasswordEmail(String toEmail, String resetLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Reset your FinFlow password");
        message.setText("Click the link below to reset your password:\n\n" + resetLink);
        message.setText("Please do not share this link with anyone.");
        mailSender.send(message);
    }
}
