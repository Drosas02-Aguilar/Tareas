package com.activities.group.Email;

import com.activities.group.DAO.IUsuario;
import com.activities.group.Entity.Usuario;
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

    public void sendResetLink(String email) {

        Usuario usuario = iUsuario.findByEmail(email).orElse(null);
        if (usuario != null) {
            String token = UUID.randomUUID().toString();
            resetTokens.put(token, usuario.getIdUsuario());

            String link = "http://localhost:8080/api/auth/reset?token=" + token;
            emailService.sendHtmlEmail(email,
                    "Recuperar contraseña",
                    "Haz clic en el siguiente enlace para restablecer tu contraseña: " + link);
        }

    }

    public boolean resetPassword(String token, String newPassword) {

        Integer idUsuario = resetTokens.remove(token);
        if (idUsuario != null) {
            Usuario usuario = iUsuario.findById(idUsuario).orElse(null);
            if (usuario != null) {
                usuario.setPassword(encoder.encode(newPassword));
            }
            iUsuario.save(usuario);
            return true;

        }

        return false;
    }

}
