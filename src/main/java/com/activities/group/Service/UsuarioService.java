package com.activities.group.Service;

import com.activities.group.DAO.ITarea;
import com.activities.group.DAO.IUsuario;
import com.activities.group.Entity.Usuario;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    @Autowired
    private IUsuario iUsuario;

    @Autowired
    private ITarea iTarea;

    @Transactional
    public Usuario CrearCuenta(Usuario usuario) {

        if (iUsuario.findByEmail(usuario.getEmail()).isPresent()) {
            return null;
        }
        if (iUsuario.findByUsername(usuario.getUsername()).isPresent()) {
            return null;
        }
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

    @Transactional
    public Usuario ActualizarUsuario(int idUsuario, Usuario usuarioUpdate) {
        Optional<Usuario> usuarioPtional = iUsuario.findById(idUsuario);
        if (usuarioPtional.isPresent()) {
            Usuario usuario = usuarioPtional.get();
            usuario.setNombre(usuarioUpdate.getNombre());
            usuario.setUsername(usuarioUpdate.getUsername());
            usuario.setEmail(usuarioUpdate.getEmail());
            usuario.setPassword(usuarioUpdate.getPassword());
            iUsuario.save(usuario);
            return usuario;
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

}
