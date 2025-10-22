package banco.models;


import jakarta.persistence.*;

@Entity
@Table(name = "rol_usuario")
public class RolUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_rol_usuario;

    private String nombre_rol;
    private String descripcion;

    // Getters y Setters

    public Long getId_rol_usuario() {
        return id_rol_usuario;
    }

    public void setId_rol_usuario(Long id_rol_usuario) {
        this.id_rol_usuario = id_rol_usuario;
    }

    public String getNombre_rol() {
        return nombre_rol;
    }

    public void setNombre_rol(String nombre_rol) {
        this.nombre_rol = nombre_rol;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}