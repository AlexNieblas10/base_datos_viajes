package org.base_datos_viajes.dao.interfaces;

import org.base_datos_viajes.exception.DatabaseException;
import org.base_datos_viajes.model.Parada;

import java.util.List;

/**
 * Interfaz específica para operaciones del DAO de Viaje.
 * Define métodos adicionales más allá del GenericDAO para obtener datos embebidos.
 */
public interface IViajeDAO {

    /**
     * Obtiene todas las paradas de un viaje específico.
     *
     * @param viajeId ID del viaje
     * @return Lista de paradas del viaje
     * @throws DatabaseException si ocurre un error en la base de datos
     */
    List<Parada> obtenerParadas(String viajeId) throws DatabaseException;
}
