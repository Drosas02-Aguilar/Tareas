package com.activities.group.Email;

import com.activities.group.DAO.IUsuario;
import com.activities.group.Entity.TokenInfo;
import com.activities.group.Entity.Usuario;
import com.activities.group.desingEmail.EmailTemplates;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class VerificationService {

    private final Map<String, TokenInfo> tokens = new ConcurrentHashMap<>();

    @Value("${app.tokens.expiryMinutes:15}")
    private long expiryMinutes;

    @Autowired private EmailService emailService;
    @Autowired private EmailTemplates emailTemplates;
    @Autowired private IUsuario iUsuario;

    public void sendActivationEmail(Usuario usuario) {
        String token = UUID.randomUUID().toString();
        LocalDateTime expiry = LocalDateTime.now().plusMinutes(expiryMinutes);
        tokens.put(token, new TokenInfo(usuario.getIdUsuario(), expiry));

        String body = emailTemplates.activacionCuenta(usuario, token);
        emailService.SendEmail(usuario.getEmail(), "Activación de cuenta", body);
    }

    public boolean verifyAccount(String token) {
        TokenInfo info = tokens.remove(token);
        if (info == null || info.isExpired()) return false;
        Usuario usuario = iUsuario.findById(info.getIdUsuario()).orElse(null);
        if (usuario == null) return false;
        usuario.setEnabled(true);
        iUsuario.save(usuario);
        return true;
    }
}

