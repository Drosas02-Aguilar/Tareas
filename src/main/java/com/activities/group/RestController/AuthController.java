package com.activities.group.RestController;

import com.activities.group.Email.EmailService;
import com.activities.group.Email.NotificationService;
import com.activities.group.Email.PasswordResetService;
import com.activities.group.Email.VerificationService;
import com.activities.group.Entity.Result;
import com.activities.group.Entity.Usuario;
import com.activities.group.Service.UsuarioService;
import com.activities.group.desingEmail.EmailTemplates;
import com.activities.group.jwt.JwtUtil;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private VerificationService verificationService;
    @Autowired
    private PasswordResetService passwordResetService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private EmailTemplates emailTemplates;
    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody Usuario usuario) {
        Result result = new Result();

        try {
            String rawPassword = usuario.getPassword();

            Usuario creado = usuarioService.CrearCuenta(usuario);

            if (creado != null) {
                String body = emailTemplates.registroUsuario(creado, rawPassword);
                emailService.sendHtmlEmail(creado.getEmail(), "Registro exitoso", body);
                result.object = "Usuario registrado. Revisa tu correo para verificar";
                result.status = 201;
            } else {
                result.status = 400;
                result.errorMessage = "No se pudo registrar el usuario (email o username ya existe)";
            }

        } catch (Exception ex) {
            result.status = 500;
            result.errorMessage = ex.getLocalizedMessage();
        }

        return ResponseEntity.status(result.status).body(result);
    }

    @GetMapping("/verify")
    public ResponseEntity verifyAccount(@RequestParam String token) {

        Result result = new Result();

        try {
            boolean verificado = verificationService.verifyAccount(token);
            if (verificado) {

                result.status = 200;
                result.errorMessage = "Cuenta verificada correctamente.";

            } else {
                result.status = 400;
                result.errorMessage = "Token invalido";

            }

        } catch (Exception ex) {

            result.status = 500;
            result.errorMessage = ex.getLocalizedMessage();

        }
        return ResponseEntity.status(result.status).body(result);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody Usuario usuario) {
        Result result = new Result();
        try {
            Usuario usuarioBuscar = usuarioService.BuscarPorUsuario(usuario.getUsername());
            if (usuarioBuscar == null) {
                result.status = 404;
                result.errorMessage = "Usuario no encontrado";
                return ResponseEntity.status(result.status).body(result);
            }

            if (!usuarioBuscar.isEnabled()) {
                verificationService.sendActivationEmail(usuarioBuscar);
                result.status = 403;
                result.errorMessage = "Cuenta no activada. Se envió un correo de activación";
                return ResponseEntity.status(result.status).body(result);
            }

            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(usuario.getUsername(), usuario.getPassword())
            );

            String token = jwtUtil.generatedToke(usuario.getUsername());

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("username", usuario.getUsername());
            response.put("idUsuario", usuarioBuscar.getIdUsuario());


            result.object = response;
            result.status = 200;

        } catch (AuthenticationException ex) {
            result.status = 401;
            result.errorMessage = "Credenciales inválidas";
        } catch (Exception ex) {
            result.status = 500;
            result.errorMessage = "Error interno: " + ex.getMessage();
        }
        return ResponseEntity.status(result.status).body(result);
    }

    @PostMapping("/forgot")
    public ResponseEntity forgotPassword(@RequestParam String email) {
        Result result = new Result();
        try {
            passwordResetService.sendResetLink(email);
            result.status = 200;
            result.errorMessage = "Se envió un correo con el link para recuperar la contraseña.";
        } catch (Exception ex) {
            result.status = 500;
            result.errorMessage = ex.getLocalizedMessage();
        }
        return ResponseEntity.status(result.status).body(result);
    }

    @PostMapping("/reset")
    public ResponseEntity resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        Result result = new Result();
        try {
            boolean reset = passwordResetService.resetPassword(token, newPassword);
            if (reset) {
                result.status = 200;
                result.errorMessage = "Contraseña restablecida correctamente";
            } else {
                result.status = 404;
                result.errorMessage = "Token invalido";
            }
        } catch (Exception ex) {
            result.status = 500;
            result.errorMessage = ex.getLocalizedMessage();
        }
        return ResponseEntity.status(result.status).body(result);
    }

    @PostMapping("/change-password")
    public ResponseEntity changePassword(@RequestParam String username,
            @RequestParam String newPassword) {

        Result result = new Result();

        try {
            Usuario usuario = usuarioService.BuscarPorUsuario(username);
            if (usuario != null) {
                usuarioService.cambiarPassword(usuario, newPassword);
                notificationService.sendpasswordChangeNotification(usuario);

                result.status = 200;
                result.errorMessage = "Contraseña cambiada";

            } else {
                result.status = 404;
                result.errorMessage = "Usuario no encontrado";
            }

        } catch (Exception ex) {
            result.status = 500;
            result.errorMessage = ex.getLocalizedMessage();
        }

        return ResponseEntity.status(result.status).body(result);
    }

}
