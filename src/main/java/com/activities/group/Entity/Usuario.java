
package com.activities.group.Entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;


@Entity
@Table(name="USUARIO")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idusuario")
    private int idUsuario;
    
    @Column(name="nombre", length = 120, nullable = false)
    private String nombre;
    
    @Column(name="username", length = 120, nullable = false)
    private String username;
    
    @Column(name="email", length = 200, nullable = false, unique = true)
    private String email;
    
    @Column(name ="password", length = 200, nullable = false, unique = true)
    private String password;
    
    @Column(name="roles", nullable = false, length = 50)
    private String roles = "ROLE_USER";
    
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Set<Tarea> tareas;
    
    

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public Set<Tarea> getTareas() {
        return tareas;
    }

    public void setTareas(Set<Tarea> tareas) {
        this.tareas = tareas;
    }
    
    
}
