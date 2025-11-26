package org.base_datos_viajes.model;

import org.bson.types.ObjectId;

import java.io.Serializable;
import java.util.Objects;

/**
 * POJO representando un usuario del sistema.
 */
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    private ObjectId id;
    private String usuario;
    private String contraseña;

    /**
     * Constructor sin argumentos requerido por MongoDB POJO codec.
     */
    public Usuario() {
    }

    /**
     * Constructor con usuario.
     */
    public Usuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * Constructor con parámetros.
     */
    public Usuario(String usuario, String contraseña) {
        this.usuario = usuario;
        this.contraseña = contraseña;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario1 = (Usuario) o;
        return Objects.equals(id, usuario1.id) ||
               Objects.equals(usuario, usuario1.usuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, usuario);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", usuario='" + usuario + '\'' +
                '}';
    }
}
