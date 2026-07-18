package com.example.celular.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false)
    private String nombre;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Ingresa un correo válido")
    @Column(nullable = false, unique = true)
    private String correo;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    @Column(nullable = false)
    private String password;

    // NUEVA RELACIÓN: ManyToOne con Rol
    @ManyToOne
    @JoinColumn(name = "rol_id", referencedColumnName = "id")
    private Rol rol;

    @Column(nullable = false)
    private boolean activo = true;

    // ========== CONSTRUCTORES ==========
    public User() {}

    public User(String nombre, String correo, String password, Rol rol) {
        this.nombre = nombre;
        this.correo = correo;
        this.password = password;
        this.rol = rol;
        this.activo = true;
    }

    // ========== GETTERS Y SETTERS ==========
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    // ========== MÉTODOS AUXILIARES ==========
    public boolean isAdmin() {
        return rol != null && "ADMIN".equalsIgnoreCase(rol.getNombre());
    }

    public boolean isAsistente() {
        return rol != null && "ASISTENTE".equalsIgnoreCase(rol.getNombre());
    }

    public boolean isUsuario() {
        return rol != null && "USUARIO".equalsIgnoreCase(rol.getNombre());
    }

    public String getRolNombre() {
        return rol != null ? rol.getNombre() : "Sin rol";
    }
}