/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.base_datos_viajes.dao.interfaces;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.base_datos_viajes.exception.DatabaseException;
import org.base_datos_viajes.model.Propietario;
import org.bson.types.ObjectId;

/**
 *
 * @author adell
 */
public interface IPropietarioDAO {

    public Propietario save(Propietario entity) throws DatabaseException;

    public List<Propietario> saveAll(List<Propietario> entities) throws DatabaseException;

    public Optional<Propietario> findById(ObjectId id) throws DatabaseException;

    public List<Propietario> findAll() throws DatabaseException;

    public long count() throws DatabaseException;

    public boolean existsById(ObjectId id) throws DatabaseException;

    public List<Propietario> findByField(String fieldName, Object value) throws DatabaseException;

    public List<Propietario> findWithPagination(int page, int pageSize) throws DatabaseException;

    public Propietario update(Propietario entity) throws DatabaseException;

    public Propietario updatePartial(ObjectId id, Map<String, Object> updates) throws DatabaseException;

    public boolean deleteById(ObjectId id) throws DatabaseException;

    public boolean delete(Propietario entity) throws DatabaseException;

    public long deleteAll() throws DatabaseException;

}
