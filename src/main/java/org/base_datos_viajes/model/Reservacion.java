package org.base_datos_viajes.model;

import org.bson.types.ObjectId;

import java.io.Serializable;
import java.time.Duration;
import java.util.Objects;

/**
 * @author Camila Zubia 00000244825
 */
public class Reservacion implements Serializable {
    
    public enum Estatus {
        ESPERA, ACEPTADA, RECHAZADA, CANCELADA, TERMINADA;
    }

    private static final long serialVersionUID = 1L;

    private ObjectId id;
    private ObjectId viajeId;
    private ObjectId paradaId;
    private ObjectId pasajeroId;
    private double precioTotal;
    private Estatus estatus;
    private Long tiempoRestante;
    
    /**
     * Constructor sin argumentos requerido por MongoDB POJO codec.
     */
    public Reservacion() {
    }

    /**
     * Constructor completo con paradas.
     * @param precio
     * @param tiempo
     * @param estatus
     */
    public Reservacion(double precio, Long tiempo, Estatus estatus) {
        this.precioTotal = precio;
        this.tiempoRestante = tiempo;
        this.estatus = estatus;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }
    
    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public Estatus getEstatus() {
        return estatus;
    }

    public void setEstatus(Estatus estatus) {
        this.estatus = estatus;
    }

    public Long getTiempoRestante() {
        return tiempoRestante;
    }

    public void setTiempoRestante(Long tiempoRestante) {
        this.tiempoRestante = tiempoRestante;
    }

    public ObjectId getViajeId() {
        return viajeId;
    }

    public void setViajeId(ObjectId viajeId) {
        this.viajeId = viajeId;
    }

    public ObjectId getParadaId() {
        return paradaId;
    }

    public void setParadaId(ObjectId paradaId) {
        this.paradaId = paradaId;
    }

    public ObjectId getPasajeroId() {
        return pasajeroId;
    }

    public void setPasajeroId(ObjectId pasajeroId) {
        this.pasajeroId = pasajeroId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservacion reservacion = (Reservacion) o;
        return Objects.equals(id, reservacion.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Viaje{" +
                "id=" + id +
                ", precio='" + precioTotal + '\'' +
                ", estatus=" + estatus +
                ", tiempo restante=" + tiempoRestante +
                '}';
    }
}
