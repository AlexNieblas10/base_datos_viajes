package org.base_datos_viajes.dao.interfaces;

import org.base_datos_viajes.exception.DatabaseException;
import org.base_datos_viajes.model.Adeudo;
import org.bson.types.ObjectId;
import java.util.List;

/**
 * Interfaz para operaciones de persistencia de Adeudo
 *
 * @author Camila Zubia 00000244825
 */
public interface IAdeudoDAO extends GenericDAO<Adeudo, ObjectId> {

    /**
     * Obtiene todos los adeudos de un conductor específico
     *
     * @param conductorId ID del conductor
     * @return Lista de adeudos del conductor
     * @throws DatabaseException si ocurre un error en la base de datos
     */
    List<Adeudo> obtenerAdeudosPorConductor(ObjectId conductorId) throws DatabaseException;
}
