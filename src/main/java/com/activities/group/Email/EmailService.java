package com.activities.group.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    
        
    @Autowired 
            JavaMailSender mailSender;
    
    
    public void SendEmail(String to, String subject, String text){
        SimpleMailMessage mensaje = new SimpleMailMessage();
        
        mensaje.setFrom("rosasaguilardamian@gmail.com");
        mensaje.setTo(to);
        mensaje.setSubject(subject);
        mensaje.setText(text);
        mailSender.send(mensaje);
    }
    
}
