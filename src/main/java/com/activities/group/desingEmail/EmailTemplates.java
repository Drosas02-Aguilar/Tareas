package com.activities.group.desingEmail;

import com.activities.group.Entity.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailTemplates {
@Value("${app.tokens.expiryMinutes:15}")
    private long expiryMinutes;

public String registroUsuario(Usuario usuario, String rawPassword) {
        return """
            <html>
            <head>
              <style>
                body { font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px; }
                .card { background: #fff; border-radius: 8px; padding: 20px; box-shadow: 0 2px 6px rgba(0,0,0,0.2); }
                h2 { color: #2c3e50; }
                p { font-size: 14px; color: #555; }
                .highlight { font-weight: bold; color: #e74c3c; }
              </style>
            </head>
            <body>
              <div class="card">
                <h2>¡Bienvenido, %s!</h2>
                <p>Tu cuenta ha sido creada exitosamente.</p>
                <p><span class="highlight">Usuario:</span> %s</p>
                <p><span class="highlight">Contraseña:</span> %s</p>
                <p>Recuerda que deberás activar tu cuenta al iniciar sesión.</p>
              </div>
            </body>
            </html>
            """.formatted(usuario.getNombre(), usuario.getUsername(), rawPassword);
    }

    public String activacionCuenta(Usuario usuario, String token) {
        String link = "http://localhost:8080/api/auth/verify?token=" + token;
        return """
            <html>
            <head>
              <style>
                body { font-family: Arial, sans-serif; background-color: #eef2f7; padding: 20px; }
                .card { background: #fff; border-radius: 8px; padding: 20px; box-shadow: 0 2px 6px rgba(0,0,0,0.1); }
                a.button { display: inline-block; padding: 10px 20px; background: #3498db; color: #fff; text-decoration: none; border-radius: 5px; }
                a.button:hover { background: #2980b9; }
              </style>
            </head>
            <body>
              <div class="card">
                <h2>Activación de cuenta</h2>
                <p>Hola %s,</p>
                <p>Haz clic en el siguiente botón para activar tu cuenta:</p>
                <p><a href='%s' class='button'>Activar cuenta</a></p>
                <p>Este enlace expira en %d minutos.</p>
              </div>
            </body>
            </html>
            """.formatted(usuario.getNombre(), link, expiryMinutes);
    }
    
}
