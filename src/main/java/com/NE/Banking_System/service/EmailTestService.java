package com.NE.Banking_System.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailTestService implements CommandLineRunner {
    private final JavaMailSender mailSender;

    @Override
    public void run(String... args) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("karlychloee12@gmail.com");
        message.setSubject("Test Email");
        message.setText("Banking system email test successful!");
        mailSender.send(message);
    }
}
