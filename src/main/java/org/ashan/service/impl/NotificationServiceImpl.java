package org.ashan.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ashan.service.NotificationService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    public boolean sendMail(String email,String subject,String name){

        try{

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(
                    message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name()
            );

            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(setEmailContext(name),true);

            mailSender.send(message);

            return true;
        } catch (Exception e) {
            log.error("An error occurred while sending mail : ",e);
            return false;
        }
    }

    private String setEmailContext(String name){

        Context context = new Context();
        context.setVariable("name",name);

        String htmlBody = templateEngine.process("email/welcome",context);

        return htmlBody;
    }


}
