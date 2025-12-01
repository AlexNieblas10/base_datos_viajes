package org.base_datos_viajes.model;

import org.bson.types.ObjectId;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * POJO representando un conductor.
 */
public class Pasajero implements Serializable {

    private static final long serialVersionUID = 1L;

    private ObjectId id;
    private String nombre;
    private double calificacion;
    private List<Reservacion> reservaciones;

    /**
     * Constructor sin argumentos requerido por MongoDB POJO codec.
     */
    public Pasajero() {
    }
    
    public Pasajero(String nombre) {
        this.reservaciones = new ArrayList<>();
        this.calificacion = 100.0;
    }

    /**
     * Constructor con parámetros.
     * @param nombre
     * @param calificacion
     * @param reservaciones
     */
    public Pasajero(String nombre, double calificacion, List<Reservacion> reservaciones) {
        this.nombre = nombre;
        this.calificacion = calificacion;
        this.reservaciones = reservaciones != null ? reservaciones : new ArrayList<>();
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(double calificacion) {
        this.calificacion = calificacion;
    }

    public List<Reservacion> getReservaciones() {
        return reservaciones;
    }

    public void setReservaciones(List<Reservacion> reservaciones) {
        this.reservaciones = reservaciones;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pasajero conductor = (Pasajero) o;
        return Objects.equals(id, conductor.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Conductor{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", calificacion=" + calificacion +
                '}';
    }
}
