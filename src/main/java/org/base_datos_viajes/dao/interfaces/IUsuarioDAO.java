
package org.base_datos_viajes.dao.interfaces;

import Entidades.Usuario;
import org.base_datos_viajes.exception.DatabaseException;
import org.bson.types.ObjectId;

/**
 *
 * @author ferch
 */
public interface IUsuarioDAO extends GenericDAO<Usuario, ObjectId>{
    
    //metodo especifico para login
    Usuario consultarPorCredenciales(String usuario, String contraseña)throws DatabaseException;
}
