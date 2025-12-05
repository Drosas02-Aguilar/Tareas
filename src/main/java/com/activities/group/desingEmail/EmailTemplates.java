package com.activities.group.desingEmail;

import com.activities.group.Entity.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailTemplates {
@Value("${app.tokens.expiryMinutes:15}")
    private long expiryMinutes;

public String registroUsuario( Usuario usuario, String rawPassword){
return "Hola " + usuario.getNombre() + ",\n\n" +
               "Tu cuenta ha sido creada.\n" +
               "Usuario: " + usuario.getUsername() + "\n" +
               "Contraseña: " + rawPassword + "\n\n" +
               "Recuerda que deberás activar tu cuenta al iniciar sesión.";
    
    
}
    public String activacionCuenta(Usuario usuario, String token){
        String link ="http://localhost:8080/api/auth/verify?token=" + token;
        return "Hola " + usuario.getNombre() + ",\n\n" +
               "Activa tu cuenta haciendo clic en el siguiente enlace:\n" + link +
               "\n\nEste enlace expira en " + expiryMinutes + " minutos.";
    
    }
    
}
