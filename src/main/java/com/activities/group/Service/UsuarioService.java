package com.activities.group.Service;

import com.activities.group.DAO.ITarea;
import com.activities.group.DAO.IUsuario;
import com.activities.group.Entity.Usuario;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    @Autowired
    private IUsuario iUsuario;

    @Autowired
    private ITarea iTarea;
    
   @Autowired
   private PasswordEncoder encoder;

    @Transactional
    public Usuario CrearCuenta(Usuario usuario) {

        if (iUsuario.findByEmail(usuario.getEmail()).isPresent()) {
            return null;
        }
        if (iUsuario.findByUsername(usuario.getUsername()).isPresent()) {
            return null;
        }
        
        usuario.setPassword(encoder.encode(usuario.getPassword()));
        iUsuario.save(usuario);
        return usuario;

    }

    public Usuario GetUsuarioByid(int idUsuario) {
        Optional<Usuario> usuario = iUsuario.findById(idUsuario);
        return usuario.isPresent() ? usuario.get() : null;

    }

    public List<Usuario> ListarUsuarios() {
        List<Usuario> usuario = iUsuario.findAll();
        return usuario;

    }

    public Usuario BuscarPorUsuario(String username) {
        Optional<Usuario> usuario = iUsuario.findByUsername(username);
        return usuario.isPresent() ? usuario.get() : null;
    }

    public Usuario BuscarPorEmail(String email) {
        Optional<Usuario> usuario = iUsuario.findByEmail(email);
        return usuario.isPresent() ? usuario.get() : null;

    }

//    @Transactional
//    public Usuario ActualizarUsuario(int idUsuario, Usuario usuarioUpdate) {
//        Optional<Usuario> usuarioPtional = iUsuario.findById(idUsuario);
//        if (usuarioPtional.isPresent()) {
//            Usuario usuario = usuarioPtional.get();
//            usuario.setNombre(usuarioUpdate.getNombre());
//            usuario.setUsername(usuarioUpdate.getUsername());
//            usuario.setEmail(usuarioUpdate.getEmail());
//if (usuarioUpdate.getPassword() != null && !usuarioUpdate.getPassword().isBlank()) {
//                usuario.setPassword(encoder.encode(usuarioUpdate.getPassword()));
//            }            iUsuario.save(usuario);
//            return usuario;
//        }
//        return null;
//    }
    @Transactional
   public Usuario ActualizarUsuarioParcial(int idUsuario, Map<String, Object> cambios) {
    Optional<Usuario> usuarioOpt = iUsuario.findById(idUsuario);
    
    if (usuarioOpt.isPresent()) {
        Usuario usuario = usuarioOpt.get();
        
        if (cambios.containsKey("nombre") && cambios.get("nombre") != null) {
            usuario.setNombre((String) cambios.get("nombre"));
        }
        
        if (cambios.containsKey("username") && cambios.get("username") != null) {
            String newUsername = (String) cambios.get("username");
            Optional<Usuario> existenteOpt = iUsuario.findByUsername(newUsername);
            if (existenteOpt.isPresent() && existenteOpt.get().getIdUsuario() != idUsuario) {
                throw new RuntimeException("El nombre de usuario ya está en uso");
            }
            usuario.setUsername(newUsername);
        }
        
        if (cambios.containsKey("email") && cambios.get("email") != null) {
            String newEmail = (String) cambios.get("email");
            Optional<Usuario> existenteOpt = iUsuario.findByEmail(newEmail);
            if (existenteOpt.isPresent() && existenteOpt.get().getIdUsuario() != idUsuario) {
                throw new RuntimeException("El correo electrónico ya está en uso");
            }
            usuario.setEmail(newEmail);
        }
        
        return iUsuario.save(usuario);
    }
    
    return null;
}

    @Transactional
    public Usuario EliminarUsuario(int IdUsusario) {
        Optional<Usuario> usuarioOptional = iUsuario.findById(IdUsusario);
        if (usuarioOptional.isPresent()) {

            Usuario usuario = usuarioOptional.get();
            iUsuario.delete(usuario);
            return usuario;

        }

        return null;

    }
    
     @Transactional
    public void cambiarPassword(Usuario usuario, String newPassword) {
        usuario.setPassword(encoder.encode(newPassword));
        iUsuario.save(usuario);
    }

}
