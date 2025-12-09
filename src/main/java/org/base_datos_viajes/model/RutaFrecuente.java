/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.base_datos_viajes.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;

/**
 *
 * @author adell
 */
public class RutaFrecuente implements Serializable {

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

    public RutaFrecuente() {
        this.paradas = new ArrayList<>();
    }

    public RutaFrecuente(String nombre, String destino, String origen, LocalDate fecha, LocalTime hora, double precioTotal, List<Parada> paradas, ObjectId conductorId) {
        this.nombre = nombre;
        this.destino = destino;
        this.origen = origen;
        this.fecha = fecha;
        this.hora = hora;
        this.precioTotal = precioTotal;
        this.paradas = paradas;
        this.conductorId = conductorId;
    }

    public RutaFrecuente(ObjectId id, String nombre, String destino, String origen, LocalDate fecha, LocalTime hora, double precioTotal, List<Parada> paradas) {
        this.id = id;
        this.nombre = nombre;
        this.destino = destino;
        this.origen = origen;
        this.fecha = fecha;
        this.hora = hora;
        this.precioTotal = precioTotal;
        this.paradas = paradas;
    }

    public RutaFrecuente(ObjectId id, String nombre, String destino, String origen, LocalDate fecha, LocalTime hora, double precioTotal) {
        this.id = id;
        this.nombre = nombre;
        this.destino = destino;
        this.origen = origen;
        this.fecha = fecha;
        this.hora = hora;
        this.precioTotal = precioTotal;
    }

    public RutaFrecuente(String nombre, String destino, String origen, LocalDate fecha, LocalTime hora, double precioTotal) {
        this.nombre = nombre;
        this.destino = destino;
        this.origen = origen;
        this.fecha = fecha;
        this.hora = hora;
        this.precioTotal = precioTotal;
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
        this.paradas = paradas;
    }

    public ObjectId getConductorId() {
        return conductorId;
    }

    public void setConductorId(ObjectId conductorId) {
        this.conductorId = conductorId;
    }

}
