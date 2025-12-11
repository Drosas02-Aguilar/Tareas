package com.activities.group.RestController;

import com.activities.group.Entity.Result;
import com.activities.group.Entity.Tarea;
import com.activities.group.Service.TareaService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/tareas")
public class RestControllerTareas {

    @Autowired
    private TareaService tareaService;

    @PostMapping("/usuario/{idUsuario}")
    public ResponseEntity<Result<Tarea>> CrearTarea(@PathVariable int idUsuario, @RequestBody Tarea tarea) {
        Result<Tarea> result = new Result<>();
        try {
            Tarea tareanueva = tareaService.CrearTarea(idUsuario, tarea);

            if (tareanueva != null) {
                result.object = tareanueva;
                result.status = 200;
            } else {
                result.status = 404;
                result.errorMessage = "Tarea no creada";
            }

        } catch (Exception ex) {
            result.status = 500;
            result.errorMessage = ex.getLocalizedMessage();
        }
        return ResponseEntity.status(result.status).body(result);
    }

    @GetMapping("/listadoTareas/{idUsuario}")
    public ResponseEntity<Result<Tarea>> ListarTareas(@PathVariable int idUsuario) {
        Result<Tarea> result = new Result<>();
        try {
            List<Tarea> tareas = tareaService.ListarTareasPorUsuario(idUsuario);
            if (!tareas.isEmpty()) {
                result.objects = tareas;
                result.status = 200;
            } else {
                result.status = 404;
                result.errorMessage = "No se encontraron tareas para el usuario.";
            }
        } catch (Exception ex) {
            result.status = 500;
            result.errorMessage = ex.getLocalizedMessage();
        }
        return ResponseEntity.status(result.status).body(result);
    }

    @GetMapping("/usuario/{idUsuario}/{idTarea}")
    public ResponseEntity<Result<Tarea>> obtenerTareaPorUsuario(
            @PathVariable int idUsuario,
            @PathVariable int idTarea) {

        Result<Tarea> result = new Result<>();
        try {
            Tarea tarea = tareaService.obtenerTareaPorUsuario(idUsuario, idTarea);
            if (tarea != null) {
                result.object = tarea;
                result.status = 200;
                result.errorMessage = "Tarea encontrada";
            } else {
                result.status = 404;
                result.errorMessage = "Tarea no encontrada o no pertenece al usuario";
            }
        } catch (Exception ex) {
            result.status = 500;
            result.errorMessage = ex.getLocalizedMessage();
        }
        return ResponseEntity.status(result.status).body(result);
    }

    @PutMapping("/usuario/{idUsuario}/{idTarea}")
    public ResponseEntity<Result<Tarea>> ActualizarTarea(@PathVariable int idUsuario, @PathVariable int idTarea, @RequestBody Tarea tareaCambios) {
        Result<Tarea> result = new Result<>();
        try {
            Tarea tarea = tareaService.ActualizarTarea(idUsuario, idTarea, tareaCambios);
            if (tarea != null) {
                result.object = tarea;
                result.status = 200;
            } else {
                result.status = 404;
                result.errorMessage = "Tarea no encontrada o no pertenece al usuario";
            }
        } catch (Exception ex) {
            result.status = 500;
            result.errorMessage = ex.getLocalizedMessage();
        }
        return ResponseEntity.status(result.status).body(result);
    }

    @DeleteMapping("/usuario/eliminacion/{idUsuario}/{idTarea}")
    public ResponseEntity<Result<Tarea>> eliminarTarea(@PathVariable int idUsuario, @PathVariable int idTarea) {

        Result<Tarea> result = new Result<>();

        try {
            Tarea tarea = tareaService.EliminarTarea(idUsuario, idTarea);
            if (tarea == null) {
                result.status = 400;
                result.errorMessage = "Tarea no encontrada o no pertenece al usuario.";
            } else {
                result.status = 200;
                result.object = tarea;
            }

        } catch (Exception ex) {
            result.status = 500;
            result.errorMessage = ex.getLocalizedMessage();
        }

        return ResponseEntity.status(result.status).body(result);
    }

    
    @PatchMapping("/usuario/{idUsuario}/{idTarea}/estado")
public ResponseEntity<Result<Tarea>> CambiarEstado(
        @PathVariable int idUsuario,
        @PathVariable int idTarea,
        @RequestBody Map<String, String> request) {

    Result<Tarea> result = new Result<>();

    try {
        String nuevoEstadoStr = request.get("estado");

        if (nuevoEstadoStr == null || nuevoEstadoStr.isEmpty()) {
            result.status = 400;
            result.errorMessage = "Estado no proporcionado";
            return ResponseEntity.status(result.status).body(result);
        }

        Tarea.EstadoTarea nuevoEstado = null;
        for (Tarea.EstadoTarea estado : Tarea.EstadoTarea.values()) {
            if (estado.name().equalsIgnoreCase(nuevoEstadoStr)) {
                nuevoEstado = estado;
                break;
            }
        }

        if (nuevoEstado == null) {
            result.status = 400;
            result.errorMessage = "Estado inválido: " + nuevoEstadoStr;
            return ResponseEntity.status(result.status).body(result);
        }

        Tarea tareaActualizada = tareaService.ActualizarEstado(idUsuario, idTarea, nuevoEstado);

        if (tareaActualizada != null) {
            result.object = tareaActualizada;
            result.status = 200;
            result.errorMessage = "Estado actualizado correctamente";
        } else {
            result.status = 404;
            result.errorMessage = "Tarea no encontrada o no pertenece al usuario";
        }

    } catch (Exception ex) {
        result.status = 500;
        result.errorMessage = ex.getLocalizedMessage();
    }

    return ResponseEntity.status(result.status).body(result);
}

    
}