package com.AniMy.services;

import com.AniMy.repository.EmailSender;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@AllArgsConstructor
public class MailSenderService implements EmailSender {
    private final static Logger logger = LoggerFactory.getLogger(MailSenderService.class);
    private final JavaMailSender javaMailSender;

    @Override
    @Async
    public void send(String to, String email) {
        try{
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(message,"UTF-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Confirm your email in AniMy");
            helper.setFrom("11410120236051@stud.cu.edu.eg");

            javaMailSender.send(message);
        }catch (MessagingException e){
            logger.error("Error sending email: {}", e.getMessage(), e);
            throw new IllegalStateException("Failed to send email", e);
        }
    }


}
