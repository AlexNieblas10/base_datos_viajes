/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.base_datos_viajes.dao.impl;

import com.mongodb.client.MongoCollection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.base_datos_viajes.config.MongoDBConnection;
import org.base_datos_viajes.dao.interfaces.GenericDAO;
import org.base_datos_viajes.exception.DatabaseException;
import org.base_datos_viajes.model.RutaFrecuente;
import org.base_datos_viajes.util.Constants;
import org.base_datos_viajes.util.ValidationUtil;
import org.bson.types.ObjectId;

/**
 *
 * @author adell
 */
public class RutasFrecuentesDAO implements GenericDAO<RutaFrecuente, ObjectId> {

    private final MongoCollection<RutaFrecuente> collection;

    public RutasFrecuentesDAO() {
        this.collection = MongoDBConnection.getInstance().getDatabase().getCollection(Constants.COLLECTION_RUTAS, RutaFrecuente.class);
    }

    @Override
    public RutaFrecuente save(RutaFrecuente entity) throws DatabaseException {
        try {
            ValidationUtil.requireNonNull(entity, "RutaFrecuente");

            if (entity.getId() == null) {
                entity.setId(new ObjectId());
            }
            collection.insertOne(entity);
            return entity;
        } catch (Exception e) {
            throw new DatabaseException("Error al guardar Ruta frecuente", e);
        }
    }

    @Override
    public List<RutaFrecuente> saveAll(List<RutaFrecuente> entities) throws DatabaseException {
        try {
            ValidationUtil.requireNonEmpty(entities, "rutas");
            for (RutaFrecuente ruta : entities) {
                if (ruta.getId() == null) {
                    ruta.setId(new ObjectId());
                }
            }
            collection.insertMany(entities);
            return entities;

        } catch (Exception e) {
            throw new DatabaseException("Error al guardar múltiples rutas", e);
        }

    }

    @Override
    public Optional<RutaFrecuente> findById(ObjectId id) throws DatabaseException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<RutaFrecuente> findAll() throws DatabaseException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public long count() throws DatabaseException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean existsById(ObjectId id) throws DatabaseException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<RutaFrecuente> findByField(String fieldName, Object value) throws DatabaseException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<RutaFrecuente> findWithPagination(int page, int pageSize) throws DatabaseException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public RutaFrecuente update(RutaFrecuente entity) throws DatabaseException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public RutaFrecuente updatePartial(ObjectId id, Map<String, Object> updates) throws DatabaseException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean deleteById(ObjectId id) throws DatabaseException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean delete(RutaFrecuente entity) throws DatabaseException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public long deleteAll() throws DatabaseException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
