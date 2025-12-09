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
        String link = "http://localhost:8083/auth/verify?token=" + token;
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

    public String recuperarPassword(Usuario usuario, String token) {

        String link = "http://localhost:8083/auth/reset?token=" + token;
        return """
               
                <html>
                           <head>
                             <style>
                               body { font-family: Arial, sans-serif; background-color: #f9f9f9; padding: 20px; }
                               .card { background: #fff; border-radius: 8px; padding: 20px; box-shadow: 0 2px 6px rgba(0,0,0,0.1); }
                               h2 { color: #2c3e50; }
                               p { font-size: 14px; color: #555; }
                               a.button { display: inline-block; padding: 10px 20px; background: #e67e22; color: #fff; text-decoration: none; border-radius: 5px; }
                               a.button:hover { background: #d35400; }
                             </style>
                           </head>
                           <body>
                             <div class="card">
                               <h2>Recuperación de contraseña</h2>
                               <p>Hola %s,</p>
                               <p>Recibimos una solicitud para restablecer tu contraseña.</p>
                               <p>Haz clic en el siguiente botón para continuar:</p>
                               <p><a href='%s' class='button'>Restablecer contraseña</a></p>
                               <p>Este enlace expira en %d minutos.</p>
                               <p>Si no solicitaste este cambio, ignora este correo.</p>
                             </div>
                           </body>
                           </html>
               
               
               """.formatted(usuario.getNombre(), link, expiryMinutes);
    }

    public String resentPasswordConfirm(Usuario usuario, String newPassword) {

        return """
          
      <html>
                      <head>
                        <style>
                          body { font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px; }
                          .card { background: #fff; border-radius: 8px; padding: 20px; box-shadow: 0 2px 6px rgba(0,0,0,0.2); }
                          h2 { color: #27ae60; }
                          p { font-size: 14px; color: #555; }
                          .highlight { font-weight: bold; color: #27ae60; }
                        </style>
                      </head>
                      <body>
                        <div class="card">
                          <h2>Tu contraseña ha sido restablecida</h2>
                          <p>Hola %s,</p>
                          <p>Tu nueva contraseña es:</p>
                          <p><span class="highlight">%s</span></p>
                          <p>Por seguridad, te recomendamos cambiarla al iniciar sesión.</p>
                        </div>
                      </body>
                      </html>    
          
    
    """.formatted(usuario.getNombre(), newPassword);

    }
    
    public String notificacionCambiosPassword(Usuario usuario){
    
    return"""
          <html>
                      <head>
                        <style>
                          body { font-family: Arial, sans-serif; background-color: #eef2f7; padding: 20px; }
                          .card { background: #fff; border-radius: 8px; padding: 20px; box-shadow: 0 2px 6px rgba(0,0,0,0.1); }
                          h2 { color: #c0392b; }
                          p { font-size: 14px; color: #555; }
                        </style>
                      </head>
                      <body>
                        <div class="card">
                          <h2>Cambio de contraseña</h2>
                          <p>Hola %s,</p>
                          <p>Tu contraseña ha sido cambiada correctamente.</p>
                          <p>Si no realizaste este cambio, contacta inmediatamente con soporte.</p>
                        </div>
                      </body>
                      </html>
          
          """.formatted(usuario.getNombre());
    
    
    }

}
