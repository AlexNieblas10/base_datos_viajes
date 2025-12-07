/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.base_datos_viajes.dao.interfaces;

import java.util.List;
import org.base_datos_viajes.exception.DatabaseException;
import org.base_datos_viajes.model.Vehiculo;

/**
 *
 * @author adell
 */
public interface IVehiculoDAO {

    public Vehiculo save(Vehiculo entity) throws DatabaseException;

    public List<Vehiculo> findByField(String fieldName, Object value) throws DatabaseException;

    public boolean delete(Vehiculo entity) throws DatabaseException;

}
