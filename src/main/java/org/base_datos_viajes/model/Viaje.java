package org.base_datos_viajes.model;

import org.bson.types.ObjectId;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * POJO representando un viaje.
 */
public class Viaje implements Serializable {

    private static final long serialVersionUID = 1L;

    private ObjectId id;
    private String nombre;
    private String destino;
    private String origen;
    private LocalDate fecha;
    private LocalTime hora;
    private double precioTotal;
    private List<Parada> paradas;

    private ObjectId conductorId; 
    private ObjectId vehiculoId;
    /**
     * Constructor sin argumentos requerido por MongoDB POJO codec.
     */
    public Viaje() {
        this.paradas = new ArrayList<>();
    }

    /**
     * Constructor con parámetros principales.
     */
    public Viaje(String nombre, String destino, String origen, LocalDate fecha, LocalTime hora, double precioTotal) {
        this.nombre = nombre;
        this.destino = destino;
        this.origen = origen;
        this.fecha = fecha;
        this.hora = hora;
        this.precioTotal = precioTotal;
        this.paradas = new ArrayList<>();
    }

    /**
     * Constructor completo con paradas.
     */
    public Viaje(String nombre, String destino, String origen, LocalDate fecha, LocalTime hora, double precioTotal, List<Parada> paradas) {
        this.nombre = nombre;
        this.destino = destino;
        this.origen = origen;
        this.fecha = fecha;
        this.hora = hora;
        this.precioTotal = precioTotal;
        this.paradas = paradas != null ? paradas : new ArrayList<>();
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

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public List<Parada> getParadas() {
        return paradas;
    }

    public void setParadas(List<Parada> paradas) {
        this.paradas = paradas != null ? paradas : new ArrayList<>();
    }

    public ObjectId getConductorId() {
        return conductorId;
    }

    public void setConductorId(ObjectId conductorId) {
        this.conductorId = conductorId;
    }

    public ObjectId getVehiculoId() {
        return vehiculoId;
    }

    public void setVehiculoId(ObjectId vehiculoId) {
        this.vehiculoId = vehiculoId;
    }
    
    

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Viaje viaje = (Viaje) o;
        return Objects.equals(id, viaje.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Viaje{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", destino='" + destino + '\'' +
                ", origen='" + origen + '\'' +
                ", fecha=" + fecha +
                ", hora=" + hora +
                ", precioTotal=" + precioTotal +
                '}';
    }
}
