package com.activities.group.Email;

import com.activities.group.DAO.IUsuario;
import com.activities.group.Entity.Usuario;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationService {

    @Autowired
    private EmailService emailService;
    @Autowired
    private IUsuario iUsuario;

    private Map<String, Integer> tokens = new ConcurrentHashMap<>();

    public void sendVerificationEmail(Usuario usuario) {

        String token = UUID.randomUUID().toString();
        tokens.put(token, usuario.getIdUsuario());
        iUsuario.save(usuario);

        String link = "http://localhost:8080/api/auth/verify?token=" + token;
        emailService.SendEmail(usuario.getEmail(),
                "Verifica tu cuenta",
                "Haz clic en el siguiente enlace para verificar tu cuenta: " + link);

    }
    
    public boolean verifyAccount(String token){
     Integer IdUsuario = tokens.remove(token);
     if(IdUsuario != null){
         Usuario usuario = iUsuario.findById(IdUsuario).orElse(null);
         if(usuario !=null){
         usuario.setEnabled(true);
         iUsuario.save(usuario);
         return true;
         
         }
     }
    
    return false;
    
    }

}
