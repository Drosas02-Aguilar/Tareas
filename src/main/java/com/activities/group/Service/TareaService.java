package com.activities.group.Service;

import com.activities.group.DAO.ITarea;
import com.activities.group.DAO.IUsuario;
import com.activities.group.Entity.Tarea;
import com.activities.group.Entity.Usuario;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TareaService {

    @Autowired
    private ITarea iTarea;

    @Autowired
    private IUsuario iUsuario;

    @Transactional
    public Tarea CrearTarea(int idUsuario, Tarea tarea) {

        Optional<Usuario> usuariOptional = iUsuario.findById(idUsuario);

        if (usuariOptional.isPresent()) {
            tarea.setUsuario(usuariOptional.get());
            if (tarea.getEstado() == null) {
                tarea.setEstado(Tarea.EstadoTarea.INICIADA);
            }
            iTarea.save(tarea);
            return tarea;
        }

        return null;
    }

    public List<Tarea> ListarTareasPorUsuario(int idUsuario) {
        return iTarea.findByUsuarioId(idUsuario);
    }

    @Transactional
    public Tarea ActualizarTarea(int idUsuario, int idTarea, Tarea tareaCambios) {
        Optional<Tarea> tareaOptional = iTarea.findOwnedById(idTarea, idUsuario);

        if (tareaOptional.isPresent()) {
            Tarea tarea = tareaOptional.get();
            tarea.setTitulo(tareaCambios.getTitulo());
            tarea.setDescripcion(tareaCambios.getDescripcion());
            tarea.setFechafin(tareaCambios.getFechafin());
            tarea.setEstado(tareaCambios.getEstado() != null ? tareaCambios.getEstado() : Tarea.EstadoTarea.INICIADA);
            iTarea.save(tarea);
            return tarea;
        }
        return null;
    }
    
    public Tarea obtenerTareaPorUsuario(int idUsuario, int idTarea) {
    Tarea tarea = iTarea.findById(idTarea).orElse(null);
    if (tarea != null && tarea.getUsuario().getIdUsuario() == idUsuario) {
        return tarea;
    }
    return null;
}


    public Tarea EliminarTarea(int idUsuario, int IdTarea) {
        Optional<Tarea> tareaOptional = iTarea.findOwnedById(IdTarea, idUsuario);
        if (tareaOptional.isPresent()) {

            Tarea tarea = tareaOptional.get();
            iTarea.delete(tarea);
            return tarea;

        }
        return null;
    }
    
    public Tarea ActualizarEstado(int idUsuario, int idTarea, Tarea.EstadoTarea nuevoEstado){
   Optional<Tarea> tareaOptional = iTarea.findOwnedById(idTarea, idUsuario);
   
   if(tareaOptional.isPresent()){
    Tarea tarea = tareaOptional.get();
    tarea.setEstado(nuevoEstado);
    iTarea.save(tarea);
    return tarea;
    }
    return null;
    }
    
}
