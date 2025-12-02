package org.base_datos_viajes.dao.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.base_datos_viajes.config.MongoDBConnection;
import org.base_datos_viajes.dao.interfaces.IAdeudoDAO;
import org.base_datos_viajes.exception.DatabaseException;
import org.base_datos_viajes.model.Adeudo;
import org.base_datos_viajes.util.Constants;
import org.base_datos_viajes.util.ValidationUtil;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.*;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.empty;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

/**
 * Implementación de operaciones CRUD para Adeudo
 *
 * @author Camila Zubia 00000244825
 */
public class AdeudoDAO implements IAdeudoDAO {

    private final MongoCollection<Adeudo> collection;

    public AdeudoDAO() {
        this.collection = MongoDBConnection.getInstance()
                .getDatabase()
                .getCollection(Constants.COLLECTION_ADEUDOS, Adeudo.class);
    }

    @Override
    public Adeudo save(Adeudo adeudo) throws DatabaseException {
        ValidationUtil.requireNonNull(adeudo, "Adeudo");

        try {
            if (adeudo.getId() == null) {
                adeudo.setId(new ObjectId());
            }

            collection.insertOne(adeudo);
            return adeudo;

        } catch (Exception e) {
            throw new DatabaseException("Error al guardar adeudo: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Adeudo> findById(ObjectId id) throws DatabaseException {
        ValidationUtil.requireNonNull(id, "ID");

        try {
            Adeudo adeudo = collection.find(eq("_id", id)).first();
            return Optional.ofNullable(adeudo);

        } catch (Exception e) {
            throw new DatabaseException("Error al buscar adeudo por ID: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Adeudo> findAll() throws DatabaseException {
        try {
            return collection.find().into(new ArrayList<>());

        } catch (Exception e) {
            throw new DatabaseException("Error al obtener todos los adeudos: " + e.getMessage(), e);
        }
    }

    @Override
    public Adeudo update(Adeudo adeudo) throws DatabaseException {
        ValidationUtil.requireNonNull(adeudo, "Adeudo");
        ValidationUtil.requireNonNull(adeudo.getId(), "ID de Adeudo");

        try {
            Bson updates = combine(
                set("conductorId", adeudo.getConductorId()),
                set("monto", adeudo.getMonto()),
                set("concepto", adeudo.getConcepto()),
                set("fecha", adeudo.getFecha()),
                set("pagado", adeudo.isPagado())
            );

            UpdateResult result = collection.updateOne(eq("_id", adeudo.getId()), updates);

            if (result.getMatchedCount() == 0) {
                throw new DatabaseException("No se encontró adeudo con ID: " + adeudo.getId());
            }

            return adeudo;

        } catch (DatabaseException e) {
            throw e;
        } catch (Exception e) {
            throw new DatabaseException("Error al actualizar adeudo: " + e.getMessage(), e);
        }
    }

    @Override
    public Adeudo updatePartial(ObjectId id, Map<String, Object> updates) throws DatabaseException {
        ValidationUtil.requireNonNull(id, "ID");
        ValidationUtil.requireNonNull(updates, "Actualizaciones");

        if (updates.isEmpty()) {
            throw new DatabaseException("El mapa de actualizaciones no puede estar vacío");
        }

        try {
            List<Bson> bsonUpdates = new ArrayList<>();
            updates.forEach((key, value) -> bsonUpdates.add(set(key, value)));

            UpdateResult result = collection.updateOne(
                eq("_id", id),
                combine(bsonUpdates)
            );

            if (result.getMatchedCount() == 0) {
                throw new DatabaseException("No se encontró adeudo con ID: " + id);
            }

            return findById(id).orElse(null);

        } catch (DatabaseException e) {
            throw e;
        } catch (Exception e) {
            throw new DatabaseException("Error al actualizar parcialmente adeudo: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean deleteById(ObjectId id) throws DatabaseException {
        ValidationUtil.requireNonNull(id, "ID");

        try {
            DeleteResult result = collection.deleteOne(eq("_id", id));
            return result.getDeletedCount() > 0;

        } catch (Exception e) {
            throw new DatabaseException("Error al eliminar adeudo: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean delete(Adeudo entity) throws DatabaseException {
        ValidationUtil.requireNonNull(entity, "Adeudo");
        ValidationUtil.requireNonNull(entity.getId(), "ID de Adeudo");

        try {
            return deleteById(entity.getId());
        } catch (Exception e) {
            throw new DatabaseException("Error al eliminar adeudo: " + e.getMessage(), e);
        }
    }

    @Override
    public long deleteAll() throws DatabaseException {
        try {
            DeleteResult result = collection.deleteMany(empty());
            return result.getDeletedCount();
        } catch (Exception e) {
            throw new DatabaseException("Error al eliminar todos los adeudos: " + e.getMessage(), e);
        }
    }

    @Override
    public long count() throws DatabaseException {
        try {
            return collection.countDocuments();

        } catch (Exception e) {
            throw new DatabaseException("Error al contar adeudos: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Adeudo> saveAll(List<Adeudo> entities) throws DatabaseException {
        ValidationUtil.requireNonNull(entities, "Lista de adeudos");

        try {
            for (Adeudo adeudo : entities) {
                if (adeudo.getId() == null) {
                    adeudo.setId(new ObjectId());
                }
            }
            collection.insertMany(entities);
            return entities;
        } catch (Exception e) {
            throw new DatabaseException("Error al guardar múltiples adeudos: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean existsById(ObjectId id) throws DatabaseException {
        ValidationUtil.requireNonNull(id, "ID");

        try {
            return collection.countDocuments(eq("_id", id)) > 0;
        } catch (Exception e) {
            throw new DatabaseException("Error al verificar existencia de adeudo: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Adeudo> findByField(String fieldName, Object value) throws DatabaseException {
        ValidationUtil.requireNonNull(fieldName, "Nombre del campo");

        try {
            return collection.find(eq(fieldName, value)).into(new ArrayList<>());
        } catch (Exception e) {
            throw new DatabaseException("Error al buscar adeudos por campo: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Adeudo> findWithPagination(int page, int pageSize) throws DatabaseException {
        if (page <= 0 || pageSize <= 0) {
            throw new DatabaseException("Página y tamaño de página deben ser mayores a 0");
        }

        try {
            return collection.find()
                    .skip((page - 1) * pageSize)
                    .limit(pageSize)
                    .into(new ArrayList<>());
        } catch (Exception e) {
            throw new DatabaseException("Error al obtener adeudos con paginación: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Adeudo> obtenerAdeudosPorConductor(ObjectId conductorId) throws DatabaseException {
        ValidationUtil.requireNonNull(conductorId, "ID de Conductor");

        try {
            return collection
                .find(eq("conductorId", conductorId))
                .into(new ArrayList<>());

        } catch (Exception e) {
            throw new DatabaseException("Error al obtener adeudos del conductor: " + e.getMessage(), e);
        }
    }
}
