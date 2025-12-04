package com.activities.group.RestController;

import com.activities.group.Entity.Result;
import com.activities.group.Entity.Usuario;
import com.activities.group.Service.UsuarioService;
import com.activities.group.jwt.JwtUtil;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

    
    
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody Usuario usuario) {
        Result result = new Result();
        try {
            Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuario.getUsername(), usuario.getPassword())
            );
            String token = jwtUtil.generatedToke(usuario.getUsername());

            Map<String, Object> resp = new HashMap<>();
            resp.put("token", token);
            resp.put("username", usuario.getUsername());

            result.object = resp;
            result.status = 200;
        } catch (AuthenticationException ex) {
            result.status = 401;
            result.errorMessage = "Credenciales inválidas.";
        }
        return ResponseEntity.status(result.status).body(result);
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody Usuario usuario) {
        Result result = new Result();
        try {
            Usuario creado = usuarioService.CrearCuenta(usuario);
            result.object = creado;
            result.status = 201;
        } catch (Exception ex) {
            result.status = 500;
            result.errorMessage = ex.getLocalizedMessage();
        }
        return ResponseEntity.status(result.status).body(result);
    }
    
}
