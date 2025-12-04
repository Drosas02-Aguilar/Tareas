package com.activities.group.Service;

import com.activities.group.DAO.IUsuario;
import com.activities.group.Entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDetalleServicio implements UserDetailsService{

    @Autowired
    private IUsuario iUsuario;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            
        Usuario usuario = iUsuario.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("Usuario no encontrado"));
        
        return  User.withUsername(usuario.getUsername())
                .password(usuario.getPassword())
                .authorities("USER") 
                .build();
    }
    
    
    
}
