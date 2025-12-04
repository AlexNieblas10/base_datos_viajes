package org.base_datos_viajes.dao.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import org.base_datos_viajes.config.MongoDBConnection;
import org.base_datos_viajes.dao.interfaces.IViajeDAO;
import org.base_datos_viajes.exception.DatabaseException;
import org.base_datos_viajes.model.Parada;
import org.base_datos_viajes.model.Viaje;
import org.base_datos_viajes.util.Constants;
import org.base_datos_viajes.util.ValidationUtil;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.base_datos_viajes.dao.interfaces.IGenericDAO;

/**
 * DAO para la entidad Viaje.
 * Implementa todas las operaciones CRUD definidas en GenericDAO y métodos específicos de IViajeDAO.
 */
public class ViajeDAO implements IGenericDAO<Viaje, ObjectId>, IViajeDAO {

    private final MongoCollection<Viaje> collection;

    public ViajeDAO() {
        this.collection = MongoDBConnection.getInstance()
                .getDatabase()
                .getCollection(Constants.COLLECTION_VIAJES, Viaje.class);
    }

    @Override
    public Viaje save(Viaje entity) throws DatabaseException {
        try {
            ValidationUtil.requireNonNull(entity, "viaje");
            if (entity.getId() == null) {
                entity.setId(new ObjectId());
            }
            collection.insertOne(entity);
            return entity;
        } catch (Exception e) {
            throw new DatabaseException("Error al guardar viaje", e);
        }
    }

    @Override
    public List<Viaje> saveAll(List<Viaje> entities) throws DatabaseException {
        try {
            ValidationUtil.requireNonEmpty(entities, "viajes");
            for (Viaje viaje : entities) {
                if (viaje.getId() == null) {
                    viaje.setId(new ObjectId());
                }
            }
            collection.insertMany(entities);
            return entities;
        } catch (Exception e) {
            throw new DatabaseException("Error al guardar múltiples viajes", e);
        }
    }

    @Override
    public Optional<Viaje> findById(ObjectId id) throws DatabaseException {
        try {
            ValidationUtil.requireNonNull(id, "id");
            Viaje viaje = collection.find(Filters.eq(Constants.FIELD_ID, id)).first();
            return Optional.ofNullable(viaje);
        } catch (Exception e) {
            throw new DatabaseException("Error al buscar viaje por ID", e);
        }
    }

    @Override
    public List<Viaje> findAll() throws DatabaseException {
        try {
            return collection.find().into(new ArrayList<>());
        } catch (Exception e) {
            throw new DatabaseException("Error al obtener todos los viajes", e);
        }
    }

    @Override
    public long count() throws DatabaseException {
        try {
            return collection.countDocuments();
        } catch (Exception e) {
            throw new DatabaseException("Error al contar viajes", e);
        }
    }

    @Override
    public boolean existsById(ObjectId id) throws DatabaseException {
        try {
            ValidationUtil.requireNonNull(id, "id");
            return collection.countDocuments(Filters.eq(Constants.FIELD_ID, id)) > 0;
        } catch (Exception e) {
            throw new DatabaseException("Error al verificar existencia de viaje", e);
        }
    }

    @Override
    public Viaje update(Viaje entity) throws DatabaseException {
        try {
            ValidationUtil.requireNonNull(entity, "viaje");
            ValidationUtil.requireNonNull(entity.getId(), "id");

            collection.replaceOne(Filters.eq(Constants.FIELD_ID, entity.getId()), entity);
            return entity;
        } catch (Exception e) {
            throw new DatabaseException("Error al actualizar viaje", e);
        }
    }

    @Override
    public Viaje updatePartial(ObjectId id, Map<String, Object> updates) throws DatabaseException {
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
            throw new DatabaseException("Error al actualizar parcialmente viaje", e);
        }
    }

    @Override
    public boolean deleteById(ObjectId id) throws DatabaseException {
        try {
            ValidationUtil.requireNonNull(id, "id");
            DeleteResult result = collection.deleteOne(Filters.eq(Constants.FIELD_ID, id));
            return result.getDeletedCount() > 0;
        } catch (Exception e) {
            throw new DatabaseException("Error al eliminar viaje", e);
        }
    }

    @Override
    public boolean delete(Viaje entity) throws DatabaseException {
        try {
            ValidationUtil.requireNonNull(entity, "viaje");
            ValidationUtil.requireNonNull(entity.getId(), "id");
            return deleteById(entity.getId());
        } catch (DatabaseException e) {
            throw new DatabaseException("Error al eliminar viaje", e);
        }
    }

    @Override
    public long deleteAll() throws DatabaseException {
        try {
            DeleteResult result = collection.deleteMany(Filters.empty());
            return result.getDeletedCount();
        } catch (Exception e) {
            throw new DatabaseException("Error al eliminar todos los viajes", e);
        }
    }

    @Override
    public List<Viaje> findByField(String fieldName, Object value) throws DatabaseException {
        try {
            ValidationUtil.requireNonEmpty(fieldName, "fieldName");
            return collection.find(Filters.eq(fieldName, value)).into(new ArrayList<>());
        } catch (Exception e) {
            throw new DatabaseException("Error al buscar viajes por campo", e);
        }
    }

    @Override
    public List<Viaje> findWithPagination(int page, int pageSize) throws DatabaseException {
        try {
            ValidationUtil.validatePositive(page, "page");
            ValidationUtil.validatePositive(pageSize, "pageSize");

            return collection.find()
                    .skip((page - 1) * pageSize)
                    .limit(pageSize)
                    .into(new ArrayList<>());
        } catch (Exception e) {
            throw new DatabaseException("Error al obtener viajes con paginación", e);
        }
    }

    // ===== Métodos específicos de IViajeDAO =====

    @Override
    public List<Parada> obtenerParadas(String viajeId) throws DatabaseException {
        try {
            ValidationUtil.validateObjectId(viajeId, "viajeId");
            Optional<Viaje> viajeOpt = findById(new ObjectId(viajeId));

            if (viajeOpt.isEmpty()) {
                return Collections.emptyList();
            }

            Viaje viaje = viajeOpt.get();
            return viaje.getParadas() != null ? viaje.getParadas() : Collections.emptyList();
        } catch (DatabaseException e) {
            throw new DatabaseException("Error al obtener paradas del viaje", e);
        }
    }

    @Override
    public void cambiarEstado(ObjectId id, boolean estado) throws DatabaseException {
        ValidationUtil.requireNonNull(id, "ID de Viaje");

        try {
            Map<String, Object> updates = new java.util.HashMap<>();
            updates.put("estaActivo", estado);
            updatePartial(id, updates);

        } catch (DatabaseException e) {
            throw new DatabaseException("Error al cambiar estado del viaje: " + e.getMessage(), e);
        }
    }
}
