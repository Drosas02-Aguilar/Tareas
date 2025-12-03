
package com.activities.group.DAO;

import com.activities.group.Entity.Tarea;
import com.activities.group.Entity.Usuario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ITarea extends JpaRepository<Tarea, Integer> {
    
    List<Tarea> findByUsuario(Usuario usuario);
    
    @Query("SELECT t FROM Tarea t WHERE t.idTarea = :idTarea AND t.usuario.idUsuario = :idUsuario")
Optional<Tarea> findOwnedById(@Param("idTarea") int idTarea, @Param("idUsuario") int idUsuario);

    
    
}
