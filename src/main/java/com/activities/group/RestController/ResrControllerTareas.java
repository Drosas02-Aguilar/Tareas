package com.activities.group.RestController;

import com.activities.group.Entity.Result;
import com.activities.group.Entity.Tarea;
import com.activities.group.Service.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
                result.errorMessage = "Usuario no creado";

            }

        } catch (Exception ex) {
            result.status = 500;
            result.errorMessage = ex.getLocalizedMessage();
        }
        return ResponseEntity.status(result.status).body(result);
    }
    
    
    

}
