package com.activities.group.Email;

import com.activities.group.DAO.IUsuario;
import com.activities.group.Entity.Usuario;
import com.activities.group.desingEmail.EmailTemplates;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordResetService {

    private Map<String, Integer> resetTokens = new ConcurrentHashMap<>();

    @Autowired
    private EmailService emailService;
    @Autowired
    private IUsuario iUsuario;
    @Autowired
    private PasswordEncoder encoder;
    
    @Autowired
    private EmailTemplates emailTemplates;
    
    

    public void sendResetLink(String email) {

        Usuario usuario = iUsuario.findByEmail(email).orElse(null);
        if (usuario != null) {
            String token = UUID.randomUUID().toString();
            resetTokens.put(token, usuario.getIdUsuario());

           String htmlBody = emailTemplates.recuperarPassword(usuario, token);
            emailService.sendHtmlEmail(email, "Recuperar contraseña", htmlBody);
            
        }

    }

    public boolean resetPassword(String token, String newPassword) {
    Integer idUsuario = resetTokens.remove(token);
    if (idUsuario != null) {
        Usuario usuario = iUsuario.findById(idUsuario).orElse(null);
        if (usuario != null) {
            // Guardar la contraseña cifrada en BD
            usuario.setPassword(encoder.encode(newPassword));
            iUsuario.save(usuario);

            // Notificar al usuario con la nueva contraseña en texto plano
            String htmlBody = emailTemplates.resentPasswordConfirm(usuario, newPassword);
            emailService.sendHtmlEmail(usuario.getEmail(), "Tu contraseña ha sido restablecida", htmlBody);

            return true;
        }
    }
    return false;
}


}
