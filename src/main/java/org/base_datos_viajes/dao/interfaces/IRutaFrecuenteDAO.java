/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.base_datos_viajes.dao.interfaces;

import java.util.List;
import org.base_datos_viajes.exception.DatabaseException;
import org.base_datos_viajes.model.Parada;
import org.base_datos_viajes.model.RutaFrecuente;

/**
 *
 * @author adell
 */
public interface IRutaFrecuenteDAO {

    public RutaFrecuente save(RutaFrecuente entity);

    public List<RutaFrecuente> findAll();

    public List<RutaFrecuente> findByField(String fieldName, Object value);

    public boolean delete(RutaFrecuente entity);

    public List<Parada> obtenerParadasRuta(String rutaId);
    
     public List<RutaFrecuente> obtenerRutasFrecuentes(String conductorId);
}
