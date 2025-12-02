/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.base_datos_viajes.dao.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.base_datos_viajes.config.MongoDBConnection;
import org.base_datos_viajes.dao.interfaces.IReservacionDAO;
import org.base_datos_viajes.exception.DatabaseException;
import org.base_datos_viajes.model.Reservacion;
import org.base_datos_viajes.util.Constants;
import org.base_datos_viajes.util.ValidationUtil;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.base_datos_viajes.dao.interfaces.IGenericDAO;

/**
 *
 * @author Usuario
 */
public class ReservacionDAO implements IGenericDAO<Reservacion, ObjectId>, IReservacionDAO{
    
    private final MongoCollection<Reservacion> collection;

    public ReservacionDAO() {
        this.collection = MongoDBConnection.getInstance()
                .getDatabase()
                .getCollection(Constants.COLLECTION_RESERVACIONES, Reservacion.class);
    }

    @Override
    public Reservacion save(Reservacion entity) throws DatabaseException {
        try {
            ValidationUtil.requireNonNull(entity, "reservacion");
            if (entity.getId() == null) {
                entity.setId(new ObjectId());
            }
            collection.insertOne(entity);
            return entity;
        } catch (Exception e) {
            throw new DatabaseException("Error al guardar reservacion", e);
        }
    }

    @Override
    public List<Reservacion> saveAll(List<Reservacion> entities) throws DatabaseException {
        try {
            ValidationUtil.requireNonEmpty(entities, "reservacion");
            for (Reservacion reservacion : entities) {
                if (reservacion.getId() == null) {
                    reservacion.setId(new ObjectId());
                }
            }
            collection.insertMany(entities);
            return entities;
        } catch (Exception e) {
            throw new DatabaseException("Error al guardar múltiples res3ervaciones", e);
        }
    }

    @Override
    public Optional<Reservacion> findById(ObjectId id) throws DatabaseException {
        try {
            ValidationUtil.requireNonNull(id, "id");
            Reservacion reservacion = collection.find(Filters.eq(Constants.FIELD_ID, id)).first();
            return Optional.ofNullable(reservacion);
        } catch (Exception e) {
            throw new DatabaseException("Error al buscar reservacion por ID", e);
        }
    }

    @Override
    public List<Reservacion> findAll() throws DatabaseException {
        try {
            return collection.find().into(new ArrayList<>());
        } catch (Exception e) {
            throw new DatabaseException("Error al obtener todas las reservaciones", e);
        }
    }

    @Override
    public long count() throws DatabaseException {
        try {
            return collection.countDocuments();
        } catch (Exception e) {
            throw new DatabaseException("Error al contar reservaciones", e);
        }
    }

    @Override
    public boolean existsById(ObjectId id) throws DatabaseException {
        try {
            ValidationUtil.requireNonNull(id, "id");
            return collection.countDocuments(Filters.eq(Constants.FIELD_ID, id)) > 0;
        } catch (Exception e) {
            throw new DatabaseException("Error al verificar existencia de reservacion", e);
        }
    }

    @Override
    public Reservacion update(Reservacion entity) throws DatabaseException {
        try {
            ValidationUtil.requireNonNull(entity, "reservacion");
            ValidationUtil.requireNonNull(entity.getId(), "id");

            collection.replaceOne(Filters.eq(Constants.FIELD_ID, entity.getId()), entity);
            return entity;
        } catch (Exception e) {
            throw new DatabaseException("Error al actualizar reservacion", e);
        }
    }

    @Override
    public Reservacion updatePartial(ObjectId id, Map<String, Object> updates) throws DatabaseException {
        try {
            ValidationUtil.requireNonNull(id, "id");
            ValidationUtil.requireNonNull(updates, "updates");
            if (updates.isEmpty()) {
                throw new DatabaseException("El mapa de actualizaciones no puede estar vacío", null);
            }

            List<Bson> updatesList = new ArrayList<>();
            for (Map.Entry<String, Object> entry : updates.entrySet()) {
                updatesList.add(Updates.set(entry.getKey(), entry.getValue()));
            }

            collection.updateOne(
                    Filters.eq(Constants.FIELD_ID, id),
                    Updates.combine(updatesList)
            );

            return findById(id).orElse(null);
        } catch (DatabaseException e) {
            throw new DatabaseException("Error al actualizar parcialmente reservacion", e);
        }
    }

    @Override
    public boolean deleteById(ObjectId id) throws DatabaseException {
        try {
            ValidationUtil.requireNonNull(id, "id");
            DeleteResult result = collection.deleteOne(Filters.eq(Constants.FIELD_ID, id));
            return result.getDeletedCount() > 0;
        } catch (Exception e) {
            throw new DatabaseException("Error al eliminar reservacion", e);
        }
    }

    @Override
    public boolean delete(Reservacion entity) throws DatabaseException {
        try {
            ValidationUtil.requireNonNull(entity, "reservacion");
            ValidationUtil.requireNonNull(entity.getId(), "id");
            return deleteById(entity.getId());
        } catch (DatabaseException e) {
            throw new DatabaseException("Error al eliminar reservacion", e);
        }
    }

    @Override
    public long deleteAll() throws DatabaseException {
        try {
            DeleteResult result = collection.deleteMany(Filters.empty());
            return result.getDeletedCount();
        } catch (Exception e) {
            throw new DatabaseException("Error al eliminar todas las reservaciones", e);
        }
    }

    @Override
    public List<Reservacion> findByField(String fieldName, Object value) throws DatabaseException {
        try {
            ValidationUtil.requireNonEmpty(fieldName, "fieldName");
            return collection.find(Filters.eq(fieldName, value)).into(new ArrayList<>());
        } catch (Exception e) {
            throw new DatabaseException("Error al buscar reservaciones por campo", e);
        }
    }

    @Override
    public List<Reservacion> findWithPagination(int page, int pageSize) throws DatabaseException {
        try {
            ValidationUtil.validatePositive(page, "page");
            ValidationUtil.validatePositive(pageSize, "pageSize");

            return collection.find()
                    .skip((page - 1) * pageSize)
                    .limit(pageSize)
                    .into(new ArrayList<>());
        } catch (Exception e) {
            throw new DatabaseException("Error al obtener reservaciones con paginación", e);
        }
    }

    @Override
    public List<Reservacion> encuentraPorIdViaje(ObjectId viajeId) {
        try {
            return collection.find(Filters.eq("viajeId", viajeId))
                    .into(new ArrayList<>());

        } catch (Exception e) {
            throw new DatabaseException("Error al buscar reservaciones por ID de viaje", e);
        }
    }
}