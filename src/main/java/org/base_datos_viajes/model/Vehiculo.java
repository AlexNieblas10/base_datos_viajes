package org.base_datos_viajes.model;

import java.io.Serializable;
import java.util.Objects;
import org.bson.types.ObjectId;

/**
 * POJO representando un vehículo.
 * Esta clase se utiliza como documento embebido en Conductor.
 */
public class Vehiculo implements Serializable {

    private static final long serialVersionUID = 1L;

    private ObjectId id;
    private String marca;
    private String modelo;
    private String color;
    private String numeroSerie;
    private String placas;
    private int capacidad;

    /**
     * Constructor sin argumentos requerido por MongoDB POJO codec.
     */
    public Vehiculo() {
    }

    /**
     * Constructor con parámetros.
     */
    public Vehiculo(String marca, String modelo, String color, String numeroSerie, String placas, int capacidad) {
        this.marca = marca;
        this.modelo = modelo;
        this.color = color;
        this.numeroSerie = numeroSerie;
        this.placas = placas;
        this.capacidad = capacidad;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getPlacas() {
        return placas;
    }

    public void setPlacas(String placas) {
        this.placas = placas;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }
    
    

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehiculo vehiculo = (Vehiculo) o;
        return Objects.equals(numeroSerie, vehiculo.numeroSerie) &&
               Objects.equals(placas, vehiculo.placas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numeroSerie, placas);
    }

    @Override
    public String toString() {
        return "Vehiculo{" +
                "marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", color='" + color + '\'' +
                ", numeroSerie='" + numeroSerie + '\'' +
                ", placas='" + placas + '\'' +
                ", capacidad=" + capacidad +
                '}';
    }
}
