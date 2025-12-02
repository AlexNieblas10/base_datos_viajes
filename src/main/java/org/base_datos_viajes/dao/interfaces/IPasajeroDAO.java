/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.base_datos_viajes.dao.interfaces;

import java.util.List;
import org.base_datos_viajes.exception.DatabaseException;
import org.base_datos_viajes.model.Pasajero;
import org.base_datos_viajes.model.Reservacion;
import org.bson.types.ObjectId;

/**
 *
 * @author Usuario
 */
public interface IPasajeroDAO extends GenericDAO<Pasajero, ObjectId>{
    
    /**
     * Obtiene todas las reservaciones de un pasajero específico.
     *
     * @param pasajeroId
     * @return Lista de reservaciones del pasajero
     * @throws DatabaseException si ocurre un error en la base de datos
     */
    List<Reservacion> obtenerReservaciones(String pasajeroId) throws DatabaseException;
    
}
