package com.laptrinhweb.thitracnghiem.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public String sendMessage(String to, String subject, String text, String returnedString) {
        SimpleMailMessage message = new SimpleMailMessage();

        try {
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            javaMailSender.send(message);
            return returnedString;
        } catch (MailException e) {
            return "Gửi mail thất bại, vui lòng thử lại!";
        }
    }
}