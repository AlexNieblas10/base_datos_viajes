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
import org.base_datos_viajes.dao.interfaces.IGenericDAO;
import org.base_datos_viajes.dao.interfaces.IParadaDAO;
import org.base_datos_viajes.exception.DatabaseException;
import org.base_datos_viajes.model.Parada;
import org.base_datos_viajes.util.Constants;
import org.base_datos_viajes.util.ValidationUtil;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

/**
 *
 * @author Usuario
 */
public class ParadaDAO implements IGenericDAO<Parada, ObjectId>, IParadaDAO{
    private final MongoCollection<Parada> collection;

    public ParadaDAO() {
        this.collection = MongoDBConnection.getInstance()
                .getDatabase()
                .getCollection(Constants.COLLECTION_PARADAS, Parada.class);
    }

    @Override
    public Parada save(Parada entity) throws DatabaseException {
        try {
            ValidationUtil.requireNonNull(entity, "parada");
            if (entity.getId() == null) {
                entity.setId(new ObjectId());
            }
            collection.insertOne(entity);
            return entity;
        } catch (Exception e) {
            throw new DatabaseException("Error al guardar parada", e);
        }
    }

    @Override
    public List<Parada> saveAll(List<Parada> entities) throws DatabaseException {
        try {
            ValidationUtil.requireNonEmpty(entities, "paradas");
            for (Parada parada : entities) {
                if (parada.getId() == null) {
                   parada.setId(new ObjectId());
                }
            }
            collection.insertMany(entities);
            return entities;
        } catch (Exception e) {
            throw new DatabaseException("Error al guardar múltiples paradas", e);
        }
    }

    @Override
    public Optional<Parada> findById(ObjectId id) throws DatabaseException {
        try {
            ValidationUtil.requireNonNull(id, "id");
            Parada parada = collection.find(Filters.eq(Constants.FIELD_ID, id)).first();
            return Optional.ofNullable(parada);
        } catch (Exception e) {
            throw new DatabaseException("Error al buscar parada por ID", e);
        }
    }

    @Override
    public List<Parada> findAll() throws DatabaseException {
        try {
            return collection.find().into(new ArrayList<>());
        } catch (Exception e) {
            throw new DatabaseException("Error al obtener todas las paradas", e);
        }
    }

    @Override
    public long count() throws DatabaseException {
        try {
            return collection.countDocuments();
        } catch (Exception e) {
            throw new DatabaseException("Error al contar paradas", e);
        }
    }

    @Override
    public boolean existsById(ObjectId id) throws DatabaseException {
        try {
            ValidationUtil.requireNonNull(id, "id");
            return collection.countDocuments(Filters.eq(Constants.FIELD_ID, id)) > 0;
        } catch (Exception e) {
            throw new DatabaseException("Error al verificar existencia de parada", e);
        }
    }

    @Override
    public Parada update(Parada entity) throws DatabaseException {
        try {
            ValidationUtil.requireNonNull(entity, "parada");
            ValidationUtil.requireNonNull(entity.getId(), "id");

            collection.replaceOne(Filters.eq(Constants.FIELD_ID, entity.getId()), entity);
            return entity;
        } catch (Exception e) {
            throw new DatabaseException("Error al actualizar parada", e);
        }
    }

    @Override
    public Parada updatePartial(ObjectId id, Map<String, Object> updates) throws DatabaseException {
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
            throw new DatabaseException("Error al actualizar parcialmente parada", e);
        }
    }

    @Override
    public boolean deleteById(ObjectId id) throws DatabaseException {
        try {
            ValidationUtil.requireNonNull(id, "id");
            DeleteResult result = collection.deleteOne(Filters.eq(Constants.FIELD_ID, id));
            return result.getDeletedCount() > 0;
        } catch (Exception e) {
            throw new DatabaseException("Error al eliminar parada", e);
        }
    }

    @Override
    public boolean delete(Parada entity) throws DatabaseException {
        try {
            ValidationUtil.requireNonNull(entity, "parada");
            ValidationUtil.requireNonNull(entity.getId(), "id");
            return deleteById(entity.getId());
        } catch (DatabaseException e) {
            throw new DatabaseException("Error al eliminar parada", e);
        }
    }

    @Override
    public long deleteAll() throws DatabaseException {
        try {
            DeleteResult result = collection.deleteMany(Filters.empty());
            return result.getDeletedCount();
        } catch (Exception e) {
            throw new DatabaseException("Error al eliminar todas las paradas", e);
        }
    }

    @Override
    public List<Parada> findByField(String fieldName, Object value) throws DatabaseException {
        try {
            ValidationUtil.requireNonEmpty(fieldName, "fieldName");
            return collection.find(Filters.eq(fieldName, value)).into(new ArrayList<>());
        } catch (Exception e) {
            throw new DatabaseException("Error al buscar paradas por campo", e);
        }
    }

    @Override
    public List<Parada> findWithPagination(int page, int pageSize) throws DatabaseException {
        try {
            ValidationUtil.validatePositive(page, "page");
            ValidationUtil.validatePositive(pageSize, "pageSize");

            return collection.find()
                    .skip((page - 1) * pageSize)
                    .limit(pageSize)
                    .into(new ArrayList<>());
        } catch (Exception e) {
            throw new DatabaseException("Error al obtener paradas con paginación", e);
        }
    }

}
