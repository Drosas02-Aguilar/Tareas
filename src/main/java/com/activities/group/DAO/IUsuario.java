
package com.activities.group.DAO;

import com.activities.group.Entity.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IUsuario extends JpaRepository<Usuario, Integer> {
    
    Optional<Usuario>findByUsername(String username);
    Optional<Usuario>findByEmail(String email);
    
    
}
