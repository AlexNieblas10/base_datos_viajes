package org.base_datos_viajes.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * POJO representando una parada en un viaje.
 * Esta clase se utiliza como documento embebido en Viaje.
 */
public class Parada implements Serializable {

    private static final long serialVersionUID = 1L;

    private String direccion;
    private double precio;

    /**
     * Constructor sin argumentos requerido por MongoDB POJO codec.
     */
    public Parada() {
    }

    /**
     * Constructor con parámetros.
     */
    public Parada(String direccion, double precio) {
        this.direccion = direccion;
        this.precio = precio;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Parada parada = (Parada) o;
        return Double.compare(parada.precio, precio) == 0 &&
               Objects.equals(direccion, parada.direccion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(direccion, precio);
    }

    @Override
    public String toString() {
        return "Parada{" +
                "direccion='" + direccion + '\'' +
                ", precio=" + precio +
                '}';
    }

    
}
