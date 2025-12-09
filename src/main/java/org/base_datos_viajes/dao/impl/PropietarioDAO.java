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
import org.base_datos_viajes.dao.interfaces.IPropietarioDAO;
import org.base_datos_viajes.exception.DatabaseException;
import org.base_datos_viajes.model.Propietario;
import org.base_datos_viajes.util.Constants;
import org.base_datos_viajes.util.ValidationUtil;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

/**
 *
 * @author adell
 */
public class PropietarioDAO implements IGenericDAO<Propietario, ObjectId>, IPropietarioDAO {

    private final MongoCollection<Propietario> collection;

    public PropietarioDAO() {
        this.collection = MongoDBConnection.getInstance().getDatabase().getCollection(Constants.COLLECTION_PROPIETARIO, Propietario.class);
    }

    @Override
    public Propietario save(Propietario entity) throws DatabaseException {
        try {
            ValidationUtil.requireNonNull(entity, "Propietario");
            if (entity.getId() == null) {
                entity.setId(new ObjectId());
            }
            collection.insertOne(entity);
            return entity;
        } catch (Exception e) {
            throw new DatabaseException("Error al guardar Propietario", e);
        }
    }

    @Override
    public List<Propietario> saveAll(List<Propietario> entities) throws DatabaseException {
        try {
            ValidationUtil.requireNonEmpty(entities, "Propietario");
            for (Propietario Propietario : entities) {
                if (Propietario.getId() == null) {
                    Propietario.setId(new ObjectId());
                }
            }
            collection.insertMany(entities);
            return entities;
        } catch (Exception e) {
            throw new DatabaseException("Error al guardar múltiples Propietarios", e);
        }
    }

    @Override
    public Optional<Propietario> findById(ObjectId id) throws DatabaseException {
        try {
            ValidationUtil.requireNonNull(id, "id");
            Propietario Propietario = collection.find(Filters.eq(Constants.FIELD_ID, id)).first();
            return Optional.ofNullable(Propietario);
        } catch (Exception e) {
            throw new DatabaseException("Error al buscar Propietario por ID", e);
        }
    }

    @Override
    public List<Propietario> findAll() throws DatabaseException {
        try {
            return collection.find().into(new ArrayList<>());
        } catch (Exception e) {
            throw new DatabaseException("Error al obtener todos los Propietarios", e);
        }
    }

    @Override
    public long count() throws DatabaseException {
        try {
            return collection.countDocuments();
        } catch (Exception e) {
            throw new DatabaseException("Error al contar Propietarios", e);
        }
    }

    @Override
    public boolean existsById(ObjectId id) throws DatabaseException {
        try {
            ValidationUtil.requireNonNull(id, "id");
            return collection.countDocuments(Filters.eq(Constants.FIELD_ID, id)) > 0;
        } catch (Exception e) {
            throw new DatabaseException("Error al verificar existencia de Propietario", e);
        }
    }

    @Override
    public List<Propietario> findByField(String fieldName, Object value) throws DatabaseException {
        try {
            ValidationUtil.requireNonEmpty(fieldName, "fieldName");
            return collection.find(Filters.eq(fieldName, value)).into(new ArrayList<>());
        } catch (Exception e) {
            throw new DatabaseException("Error al buscar Propietarios por campo", e);
        }
    }

    @Override
    public List<Propietario> findWithPagination(int page, int pageSize) throws DatabaseException {
        try {
            ValidationUtil.validatePositive(page, "page");
            ValidationUtil.validatePositive(pageSize, "pageSize");

            return collection.find()
                    .skip((page - 1) * pageSize)
                    .limit(pageSize)
                    .into(new ArrayList<>());
        } catch (Exception e) {
            throw new DatabaseException("Error al obtener Propietarios con paginación", e);
        }
    }

    @Override
    public Propietario update(Propietario entity) throws DatabaseException {
        try {
            ValidationUtil.requireNonNull(entity, "Propietario");
            ValidationUtil.requireNonNull(entity.getId(), "id");

            collection.replaceOne(Filters.eq(Constants.FIELD_ID, entity.getId()), entity);
            return entity;
        } catch (Exception e) {
            throw new DatabaseException("Error al actualizar Propietario", e);
        }
    }

    @Override
    public Propietario updatePartial(ObjectId id, Map<String, Object> updates) throws DatabaseException {
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
            throw new DatabaseException("Error al actualizar parcialmente Propietario", e);
        }
    }

    @Override
    public boolean deleteById(ObjectId id) throws DatabaseException {
        try {
            ValidationUtil.requireNonNull(id, "id");
            DeleteResult result = collection.deleteOne(Filters.eq(Constants.FIELD_ID, id));
            return result.getDeletedCount() > 0;
        } catch (Exception e) {
            throw new DatabaseException("Error al eliminar Propietario", e);
        }
    }

    @Override
    public boolean delete(Propietario entity) throws DatabaseException {
        try {
            ValidationUtil.requireNonNull(entity, "Propietario");
            ValidationUtil.requireNonNull(entity.getId(), "id");
            return deleteById(entity.getId());
        } catch (DatabaseException e) {
            throw new DatabaseException("Error al eliminar Propietario", e);
        }
    }

    @Override
    public long deleteAll() throws DatabaseException {
        try {
            DeleteResult result = collection.deleteMany(Filters.empty());
            return result.getDeletedCount();
        } catch (Exception e) {
            throw new DatabaseException("Error al eliminar todos los Propietarios", e);
        }
    }

}
