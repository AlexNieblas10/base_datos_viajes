
package org.base_datos_viajes.dao.interfaces;

import Entidades.Viaje;
import java.util.List;
import org.base_datos_viajes.exception.DatabaseException;
import org.bson.types.ObjectId;

/**
 *
 * @author ferch
 */
public interface IViajeDAO extends GenericDAO<Viaje, ObjectId>{
    
    //metodo especifico para la consulta por conductor
    List<Viaje> consultarPorConductor(ObjectId idConductor)throws DatabaseException;
}
