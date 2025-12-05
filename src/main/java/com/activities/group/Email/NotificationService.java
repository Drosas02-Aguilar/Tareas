
package com.activities.group.Email;

import com.activities.group.Entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class NotificationService {
    
    @Autowired private EmailService emailService;
    
    public void sendpasswordChangeNotification(Usuario usuario){
    emailService.SendEmail(usuario.getEmail(),
            "Contraseña actualizada",
            "Tu contraseña ha sido cambiada exitosamentee. si no fuiste tu, contacta a soporte"
            );
        
    
    }
    
}
