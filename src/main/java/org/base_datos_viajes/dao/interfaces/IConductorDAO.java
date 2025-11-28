package org.base_datos_viajes.dao.interfaces;

import org.base_datos_viajes.exception.DatabaseException;
import org.base_datos_viajes.model.Vehiculo;
import org.base_datos_viajes.model.Viaje;

import java.util.List;
import org.base_datos_viajes.model.Conductor;
import org.bson.types.ObjectId;

/**
 * Interfaz específica para operaciones del DAO de Conductor.
 * Define métodos adicionales más allá del GenericDAO para obtener datos embebidos.
 */
public interface IConductorDAO extends GenericDAO<Conductor, ObjectId>{

    /**
     * Obtiene todos los viajes de un conductor específico.
     *
     * @param conductorId ID del conductor
     * @return Lista de viajes del conductor
     * @throws DatabaseException si ocurre un error en la base de datos
     */
    List<Viaje> obtenerViajes(String conductorId) throws DatabaseException;

    /**
     * Obtiene todos los vehículos de un conductor específico.
     *
     * @param conductorId ID del conductor
     * @return Lista de vehículos del conductor
     * @throws DatabaseException si ocurre un error en la base de datos
     */
    List<Vehiculo> obtenerVehiculos(String conductorId) throws DatabaseException;
}
