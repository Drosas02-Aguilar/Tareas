package com.activities.group.RestController;

import com.activities.group.Entity.Result;
import com.activities.group.Entity.Usuario;
import com.activities.group.Service.UsuarioService;
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
@RequestMapping("api/usuario")
public class RestControllerUsuario {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity CrearCuenta(@RequestBody Usuario usuario) {
        Result result = new Result();
        try {
            if (usuario != null) {
                result.object = usuarioService.CrearCuenta(usuario);
                result.status = 201;
            } else {
                result.status = 400;
                result.errorMessage = "Usuario o email invalido";
            }
        } catch (Exception ex) {

            result.status = 500;
            result.errorMessage = ex.getLocalizedMessage();
        }
        return ResponseEntity.status(result.status).body(result);
    }

    @GetMapping("{idUsuario}")
    public ResponseEntity GetUsuarioByid(@PathVariable("idUsuario") int idUsuario) {
        Result result = new Result();
        try {
            result.object = usuarioService.GetUsuarioByid(idUsuario);
            result.status = result.object != null ? 201 : 400;
            result.errorMessage = result.object != null ? "Usuario encontrado" : "Usuario no encontrado";

        } catch (Exception ex) {
            result.status = 500;
            result.errorMessage = ex.getLocalizedMessage();
        }
        return ResponseEntity.status(result.status).body(result);
    }

    @GetMapping
    public ResponseEntity ListarUsuarios() {
        Result result = new Result();
        try {
            result.object = usuarioService.ListarUsuarios();
            result.status = 200;
        } catch (Exception ex) {
            result.status = 500;
            result.errorMessage = ex.getLocalizedMessage();
        }
        return ResponseEntity.status(result.status).body(result);
    }

    @GetMapping("/buscar/username/{username}")
    public ResponseEntity BuscarPorUsuario(@PathVariable String username) {
        Result result = new Result();
        try {
            if (username != null) {

                result.object = usuarioService.BuscarPorUsuario(username);
                result.status = 200;
            } else {
                result.status = 400;
                result.errorMessage = "Usuario no encontrado";
            }
        } catch (Exception ex) {
            result.status = 500;
            result.errorMessage = ex.getLocalizedMessage();
        }
        return ResponseEntity.status(result.status).body(result);
    }

    @GetMapping("/buscar/email/{email}")
    public ResponseEntity BuscarPorEmail(@PathVariable String email) {
        Result result = new Result();
        try {
            if (email != null) {
                result.object = usuarioService.BuscarPorEmail(email);
                result.status = 200;
            } else {
                result.status = 400;
                result.errorMessage = "Email no encontrado";
            }
        } catch (Exception ex) {
            result.status = 500;
            result.errorMessage = ex.getLocalizedMessage();
        }
        return ResponseEntity.status(result.status).body(result);
    }

    @PutMapping("/actualizar/{idUsuario}")
    public ResponseEntity ActualizarUsuario(@PathVariable int idUsuario, @RequestBody Usuario usuarioUpdate) {
        Result result = new Result();
        try {
            if (usuarioUpdate != null) {
                Usuario actualizado = usuarioService.ActualizarUsuario(idUsuario, usuarioUpdate);
                if (actualizado != null) {
                    result.object = actualizado;
                    result.status = 200;
                } else {
                    result.status = 404;
                    result.errorMessage = "Usuario no encontrado.";
                }
            } else {
                result.status = 400;
                result.errorMessage = "Datos de usuario inválidos.";
            }
        } catch (Exception ex) {
            result.status = 500;
            result.errorMessage = ex.getLocalizedMessage();
        }
        return ResponseEntity.status(result.status).body(result);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity EliminarUsuario(@PathVariable int idUsuario) {
        Result result = new Result();
        try {
            Usuario usuario = usuarioService.EliminarUsuario(idUsuario);
            if (usuario == null) {
                result.status = 400;
                result.errorMessage = "Este usuario no se encontro";
            } else {
                result.status = 200;
                result.object = usuario;
            }
        } catch (Exception ex) {
            result.status = 500;
            result.errorMessage = ex.getLocalizedMessage();

        }
        return ResponseEntity.status(result.status).body(result);
    }

}
