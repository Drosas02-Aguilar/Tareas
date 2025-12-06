
package com.activities.group.Email;

import com.activities.group.Entity.Usuario;
import com.activities.group.desingEmail.EmailTemplates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class NotificationService {
    
    @Autowired private EmailService emailService;
    @Autowired private EmailTemplates emailTemplates;
    
    
    public void sendpasswordChangeNotification(Usuario usuario){
    String htmlBody = emailTemplates.notificacionCambiosPassword(usuario);
    emailService.sendHtmlEmail(usuario.getEmail(), "Contraseña actualizada", htmlBody);
        
    
    
    }
    
}
