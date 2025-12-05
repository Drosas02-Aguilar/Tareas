package com.activities.group.Email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    
        
    @Autowired 
            JavaMailSender mailSender;
    
    public void sendHtmlEmail(String to, String subject, String htmlContent) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper mensaje = new MimeMessageHelper(message, true, "UTF-8");

            mensaje.setFrom("rosasaguilardamian@gmail.com");
            mensaje.setTo(to);
            mensaje.setSubject(subject);
            mensaje.setText(htmlContent, true); 

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Error al enviar correo HTML", e);
        }
    }
    
    
}
