package org.base_datos_viajes.model;

import org.bson.types.ObjectId;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * POJO representando un conductor.
 */
public class Conductor implements Serializable {

    private static final long serialVersionUID = 1L;

    private ObjectId id;
    private String nombre;
    private double calificacion;
    private List<Viaje> viajes;
    private List<Vehiculo> vehiculos;

    /**
     * Constructor sin argumentos requerido por MongoDB POJO codec.
     */
    public Conductor() {
        this.viajes = new ArrayList<>();
        this.vehiculos = new ArrayList<>();
    }

    /**
     * Constructor con parámetros.
     */
    public Conductor(String nombre, double calificacion, List<Viaje> viajes, List<Vehiculo> vehiculos) {
        this.nombre = nombre;
        this.calificacion = calificacion;
        this.viajes = viajes != null ? viajes : new ArrayList<>();
        this.vehiculos = vehiculos != null ? vehiculos : new ArrayList<>();
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

    public List<Viaje> getViajes() {
        return viajes;
    }

    public void setViajes(List<Viaje> viajes) {
        this.viajes = viajes != null ? viajes : new ArrayList<>();
    }

    public List<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public void setVehiculos(List<Vehiculo> vehiculos) {
        this.vehiculos = vehiculos != null ? vehiculos : new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conductor conductor = (Conductor) o;
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
