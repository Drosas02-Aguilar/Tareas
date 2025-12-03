package com.activities.group.RestController;

import com.activities.group.Entity.Result;
import com.activities.group.Entity.Tarea;
import com.activities.group.Service.TareaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/tareas")
public class ResrControllerTareas {

    @Autowired
    private TareaService tareaService;

    @PostMapping("/usuario/{idUsuario}")
    public ResponseEntity CrearTarea(@PathVariable int idUsuario, @RequestBody Tarea tarea) {
        Result result = new Result();
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
    public ResponseEntity ListarTareas(@PathVariable int idUsuario) {

        Result result = new Result();
        try {
            List<Tarea> tareas = tareaService.ListarTareasPorUsuario(idUsuario);
            if (!tareas.isEmpty()) {
                result.object = tareas;
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

    @PutMapping("/usuario/{idUsuario}/{idTarea}")
    public ResponseEntity ActualizarTarea(@PathVariable int idUsuario, @PathVariable int idTarea, @RequestBody Tarea tareaCambios) {
        Result result = new Result();
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

    @DeleteMapping("usuario/eliminacion/{idUsuario}/{idTarea}")
    public ResponseEntity EliminarTarea(@PathVariable int IdUsuario) {

        Result result = new Result();

        try {

            Tarea tarea = tareaService.EliminarTarea(IdUsuario, IdUsuario);
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

}
