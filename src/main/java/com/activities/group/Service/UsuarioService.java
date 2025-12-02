package com.activities.group.Service;

import com.activities.group.DAO.ITarea;
import com.activities.group.DAO.IUsuario;
import com.activities.group.Entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UsuarioService {
    
    @Autowired
    private IUsuario iUsuario;
    
    @Autowired
    private ITarea iTarea;
    
    
    public Usuario CrearCuenta(Usuario usuario){
        
        
        
        
            
    return usuario;
    
    }
    
    
}
