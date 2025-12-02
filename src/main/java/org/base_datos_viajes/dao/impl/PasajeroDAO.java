package org.base_datos_viajes.dao.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import org.base_datos_viajes.config.MongoDBConnection;
import org.base_datos_viajes.dao.interfaces.GenericDAO;
import org.base_datos_viajes.exception.DatabaseException;
import org.base_datos_viajes.util.Constants;
import org.base_datos_viajes.util.ValidationUtil;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.base_datos_viajes.dao.interfaces.IPasajeroDAO;
import org.base_datos_viajes.model.Pasajero;
import org.base_datos_viajes.model.Reservacion;

/**
 * DAO para la entidad Pasajero
 * Implementa todas las operaciones CRUD definidas en GenericDAO y métodos específicos de IPasajeroDAO.
 */
public class PasajeroDAO implements GenericDAO<Pasajero, ObjectId>, IPasajeroDAO {

    private final MongoCollection<Pasajero> collection;

    public PasajeroDAO() {
        this.collection = MongoDBConnection.getInstance()
                .getDatabase()
                .getCollection(Constants.COLLECTION_PASAJEROS, Pasajero.class);
    }

    @Override
    public Pasajero save(Pasajero entity) throws DatabaseException {
        try {
            ValidationUtil.requireNonNull(entity, "pasajero");
            if (entity.getId() == null) {
                entity.setId(new ObjectId());
            }
            collection.insertOne(entity);
            return entity;
        } catch (Exception e) {
            throw new DatabaseException("Error al guardar pasajero", e);
        }
    }

    @Override
    public List<Pasajero> saveAll(List<Pasajero> entities) throws DatabaseException {
        try {
            ValidationUtil.requireNonEmpty(entities, "conductores");
            for (Pasajero pasajero : entities) {
                if (pasajero.getId() == null) {
                    pasajero.setId(new ObjectId());
                }
            }
            collection.insertMany(entities);
            return entities;
        } catch (Exception e) {
            throw new DatabaseException("Error al guardar múltiples pasajeros", e);
        }
    }

    @Override
    public Optional<Pasajero> findById(ObjectId id) throws DatabaseException {
        try {
            ValidationUtil.requireNonNull(id, "id");
            Pasajero pasajero = collection.find(Filters.eq(Constants.FIELD_ID, id)).first();
            return Optional.ofNullable(pasajero);
        } catch (Exception e) {
            throw new DatabaseException("Error al buscar pasajero por ID", e);
        }
    }

    @Override
    public List<Pasajero> findAll() throws DatabaseException {
        try {
            return collection.find().into(new ArrayList<>());
        } catch (Exception e) {
            throw new DatabaseException("Error al obtener todos los pasajeros", e);
        }
    }

    @Override
    public long count() throws DatabaseException {
        try {
            return collection.countDocuments();
        } catch (Exception e) {
            throw new DatabaseException("Error al contar pasajeros", e);
        }
    }

    @Override
    public boolean existsById(ObjectId id) throws DatabaseException {
        try {
            ValidationUtil.requireNonNull(id, "id");
            return collection.countDocuments(Filters.eq(Constants.FIELD_ID, id)) > 0;
        } catch (Exception e) {
            throw new DatabaseException("Error al verificar existencia de pasajero", e);
        }
    }

    @Override
    public Pasajero update(Pasajero entity) throws DatabaseException {
        try {
            ValidationUtil.requireNonNull(entity, "pasajero");
            ValidationUtil.requireNonNull(entity.getId(), "id");

            collection.replaceOne(Filters.eq(Constants.FIELD_ID, entity.getId()), entity);
            return entity;
        } catch (Exception e) {
            throw new DatabaseException("Error al actualizar pasajero", e);
        }
    }

    @Override
    public Pasajero updatePartial(ObjectId id, Map<String, Object> updates) throws DatabaseException {
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
            throw new DatabaseException("Error al actualizar parcialmente pasajero", e);
        }
    }

    @Override
    public boolean deleteById(ObjectId id) throws DatabaseException {
        try {
            ValidationUtil.requireNonNull(id, "id");
            DeleteResult result = collection.deleteOne(Filters.eq(Constants.FIELD_ID, id));
            return result.getDeletedCount() > 0;
        } catch (Exception e) {
            throw new DatabaseException("Error al eliminar pasajero", e);
        }
    }

    @Override
    public boolean delete(Pasajero entity) throws DatabaseException {
        try {
            ValidationUtil.requireNonNull(entity, "pasajero");
            ValidationUtil.requireNonNull(entity.getId(), "id");
            return deleteById(entity.getId());
        } catch (DatabaseException e) {
            throw new DatabaseException("Error al eliminar pasajero", e);
        }
    }

    @Override
    public long deleteAll() throws DatabaseException {
        try {
            DeleteResult result = collection.deleteMany(Filters.empty());
            return result.getDeletedCount();
        } catch (Exception e) {
            throw new DatabaseException("Error al eliminar todos los pasajeros", e);
        }
    }

    @Override
    public List<Pasajero> findByField(String fieldName, Object value) throws DatabaseException {
        try {
            ValidationUtil.requireNonEmpty(fieldName, "fieldName");
            return collection.find(Filters.eq(fieldName, value)).into(new ArrayList<>());
        } catch (Exception e) {
            throw new DatabaseException("Error al buscar pasajeros por campo", e);
        }
    }

    @Override
    public List<Pasajero> findWithPagination(int page, int pageSize) throws DatabaseException {
        try {
            ValidationUtil.validatePositive(page, "page");
            ValidationUtil.validatePositive(pageSize, "pageSize");

            return collection.find()
                    .skip((page - 1) * pageSize)
                    .limit(pageSize)
                    .into(new ArrayList<>());
        } catch (Exception e) {
            throw new DatabaseException("Error al obtener pasajeros con paginación", e);
        }
    }

    // ===== Métodos específicos de IConductorDAO =====

    @Override
    public List<Reservacion> obtenerReservaciones(String pasajeroId) throws DatabaseException {
        try {
        ValidationUtil.validateObjectId(pasajeroId, "pasajeroId");
        
        //Consultar la coleccion de reservaciones por la referencia
        MongoCollection<Reservacion> reservacionCollection = MongoDBConnection.getInstance()
                .getDatabase()
                .getCollection(Constants.COLLECTION_RESERVACIONES, Reservacion.class);

        // buscar todas las reservaciones donde pasajeroId coincida
        return reservacionCollection.find(Filters.eq("pasajeroId", new ObjectId(pasajeroId)))
                .into(new ArrayList<>());
        } catch (Exception e) {
            throw new DatabaseException("Error al obtener las reservaciones del pasajero", e);
        }
    }
    
}
