package com.app.finflow.serviceImpl;

import com.app.finflow.service.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring6.SpringTemplateEngine;

import org.thymeleaf.context.Context;

import java.io.UnsupportedEncodingException;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    public void sendResetPasswordEmail(String toEmail, String resetLink) throws MessagingException, UnsupportedEncodingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        helper.setFrom("noreply.finflow@gmail.com", "FinFlow Support");
        helper.setTo(toEmail);
        helper.setSubject("Reset your FinFlow password");

        Context context = new Context();
        context.setVariable("resetLink", resetLink);
        String htmlContent = templateEngine.process("password-reset-template", context);

        helper.setText(htmlContent, true);
        mailSender.send(mimeMessage);
    }

    public void sendVerificationEmail(String to, String verificationLink) throws MessagingException, UnsupportedEncodingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        helper.setFrom("noreply.finflow@gmail.com", "FinFlow Support");
        helper.setTo(to);
        helper.setSubject("Verify your FinFlow account");

        Context context = new Context();
        context.setVariable("verificationLink", verificationLink);
        String htmlContent = templateEngine.process("verification-email-template", context);

        helper.setText(htmlContent, true);
        mailSender.send(mimeMessage);
    }
}
