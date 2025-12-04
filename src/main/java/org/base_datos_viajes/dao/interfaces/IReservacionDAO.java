/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.base_datos_viajes.dao.interfaces;

import java.util.List;
import org.base_datos_viajes.model.Reservacion;
import org.bson.types.ObjectId;

/**
 *
 * @author Usuario
 */
public interface IReservacionDAO extends IGenericDAO<Reservacion, ObjectId>{
    
    public List<Reservacion> encuentraPorIdViaje(ObjectId viajeId);
    
}
