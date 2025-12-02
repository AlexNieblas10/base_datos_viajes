package org.base_datos_viajes.dao.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import org.base_datos_viajes.config.MongoDBConnection;
import org.base_datos_viajes.dao.interfaces.GenericDAO;
import org.base_datos_viajes.dao.interfaces.IConductorDAO;
import org.base_datos_viajes.exception.DatabaseException;
import org.base_datos_viajes.model.Conductor;
import org.base_datos_viajes.model.Vehiculo;
import org.base_datos_viajes.model.Viaje;
import org.base_datos_viajes.util.Constants;
import org.base_datos_viajes.util.ValidationUtil;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * DAO para la entidad Conductor.
 * Implementa todas las operaciones CRUD definidas en GenericDAO y métodos específicos de IConductorDAO.
 */
public class ConductorDAO implements GenericDAO<Conductor, ObjectId>, IConductorDAO {

    private final MongoCollection<Conductor> collection;

    public ConductorDAO() {
        this.collection = MongoDBConnection.getInstance()
                .getDatabase()
                .getCollection(Constants.COLLECTION_CONDUCTORES, Conductor.class);
    }

    @Override
    public Conductor save(Conductor entity) throws DatabaseException {
        try {
            ValidationUtil.requireNonNull(entity, "conductor");
            if (entity.getId() == null) {
                entity.setId(new ObjectId());
            }
            collection.insertOne(entity);
            return entity;
        } catch (Exception e) {
            throw new DatabaseException("Error al guardar conductor", e);
        }
    }

    @Override
    public List<Conductor> saveAll(List<Conductor> entities) throws DatabaseException {
        try {
            ValidationUtil.requireNonEmpty(entities, "conductores");
            for (Conductor conductor : entities) {
                if (conductor.getId() == null) {
                    conductor.setId(new ObjectId());
                }
            }
            collection.insertMany(entities);
            return entities;
        } catch (Exception e) {
            throw new DatabaseException("Error al guardar múltiples conductores", e);
        }
    }

    @Override
    public Optional<Conductor> findById(ObjectId id) throws DatabaseException {
        try {
            ValidationUtil.requireNonNull(id, "id");
            Conductor conductor = collection.find(Filters.eq(Constants.FIELD_ID, id)).first();
            return Optional.ofNullable(conductor);
        } catch (Exception e) {
            throw new DatabaseException("Error al buscar conductor por ID", e);
        }
    }

    @Override
    public List<Conductor> findAll() throws DatabaseException {
        try {
            return collection.find().into(new ArrayList<>());
        } catch (Exception e) {
            throw new DatabaseException("Error al obtener todos los conductores", e);
        }
    }

    @Override
    public long count() throws DatabaseException {
        try {
            return collection.countDocuments();
        } catch (Exception e) {
            throw new DatabaseException("Error al contar conductores", e);
        }
    }

    @Override
    public boolean existsById(ObjectId id) throws DatabaseException {
        try {
            ValidationUtil.requireNonNull(id, "id");
            return collection.countDocuments(Filters.eq(Constants.FIELD_ID, id)) > 0;
        } catch (Exception e) {
            throw new DatabaseException("Error al verificar existencia de conductor", e);
        }
    }

    @Override
    public Conductor update(Conductor entity) throws DatabaseException {
        try {
            ValidationUtil.requireNonNull(entity, "conductor");
            ValidationUtil.requireNonNull(entity.getId(), "id");

            collection.replaceOne(Filters.eq(Constants.FIELD_ID, entity.getId()), entity);
            return entity;
        } catch (Exception e) {
            throw new DatabaseException("Error al actualizar conductor", e);
        }
    }

    @Override
    public Conductor updatePartial(ObjectId id, Map<String, Object> updates) throws DatabaseException {
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
            throw new DatabaseException("Error al actualizar parcialmente conductor", e);
        }
    }

    @Override
    public boolean deleteById(ObjectId id) throws DatabaseException {
        try {
            ValidationUtil.requireNonNull(id, "id");
            DeleteResult result = collection.deleteOne(Filters.eq(Constants.FIELD_ID, id));
            return result.getDeletedCount() > 0;
        } catch (Exception e) {
            throw new DatabaseException("Error al eliminar conductor", e);
        }
    }

    @Override
    public boolean delete(Conductor entity) throws DatabaseException {
        try {
            ValidationUtil.requireNonNull(entity, "conductor");
            ValidationUtil.requireNonNull(entity.getId(), "id");
            return deleteById(entity.getId());
        } catch (DatabaseException e) {
            throw new DatabaseException("Error al eliminar conductor", e);
        }
    }

    @Override
    public long deleteAll() throws DatabaseException {
        try {
            DeleteResult result = collection.deleteMany(Filters.empty());
            return result.getDeletedCount();
        } catch (Exception e) {
            throw new DatabaseException("Error al eliminar todos los conductores", e);
        }
    }

    @Override
    public List<Conductor> findByField(String fieldName, Object value) throws DatabaseException {
        try {
            ValidationUtil.requireNonEmpty(fieldName, "fieldName");
            return collection.find(Filters.eq(fieldName, value)).into(new ArrayList<>());
        } catch (Exception e) {
            throw new DatabaseException("Error al buscar conductores por campo", e);
        }
    }

    @Override
    public List<Conductor> findWithPagination(int page, int pageSize) throws DatabaseException {
        try {
            ValidationUtil.validatePositive(page, "page");
            ValidationUtil.validatePositive(pageSize, "pageSize");

            return collection.find()
                    .skip((page - 1) * pageSize)
                    .limit(pageSize)
                    .into(new ArrayList<>());
        } catch (Exception e) {
            throw new DatabaseException("Error al obtener conductores con paginación", e);
        }
    }

    // ===== Métodos específicos de IConductorDAO =====

    @Override
    public List<Viaje> obtenerViajes(String conductorId) throws DatabaseException {
        try {
        ValidationUtil.validateObjectId(conductorId, "conductorId");

        MongoCollection<Viaje> viajeCollection = MongoDBConnection.getInstance()
                .getDatabase()
                .getCollection(Constants.COLLECTION_VIAJES, Viaje.class);

        LocalDate hoy = LocalDate.now();

        return viajeCollection.find(Filters.and(
                Filters.eq("conductorId", new ObjectId(conductorId)),
                Filters.eq("estaActivo", true),
                Filters.gte("fecha", hoy)
        )).into(new ArrayList<>());
        } catch (Exception e) {
            throw new DatabaseException("Error al obtener viajes del conductor", e);
        }
    }

    @Override
    public List<Vehiculo> obtenerVehiculos(String conductorId) throws DatabaseException {
        try {
            ValidationUtil.validateObjectId(conductorId, "conductorId");
            Optional<Conductor> conductorOpt = findById(new ObjectId(conductorId));

            if (conductorOpt.isEmpty()) {
                return Collections.emptyList();
            }

            Conductor conductor = conductorOpt.get();
            return conductor.getVehiculos() != null ? conductor.getVehiculos() : Collections.emptyList();
        } catch (DatabaseException e) {
            throw new DatabaseException("Error al obtener vehículos del conductor", e);
        }
    }
}
