
package com.activities.group.DAO;

import com.activities.group.Entity.Tarea;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ITarea extends JpaRepository<Tarea, Integer> {
    
    List<Tarea>findbyUsuario(int IdUsuario);
    
    Optional<Tarea>findbyTareas(int idTarea, int idUsuario);
    
    
}
