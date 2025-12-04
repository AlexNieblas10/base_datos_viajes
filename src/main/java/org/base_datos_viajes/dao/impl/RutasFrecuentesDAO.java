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
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.base_datos_viajes.config.MongoDBConnection;
import org.base_datos_viajes.exception.DatabaseException;
import org.base_datos_viajes.model.Conductor;
import org.base_datos_viajes.model.RutaFrecuente;
import org.base_datos_viajes.util.Constants;
import org.base_datos_viajes.util.ValidationUtil;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.base_datos_viajes.dao.interfaces.IGenericDAO;
import org.base_datos_viajes.dao.interfaces.IRutaFrecuenteDAO;
import org.base_datos_viajes.model.Parada;

/**
 *
 * @author adell
 */
public class RutasFrecuentesDAO implements IGenericDAO<RutaFrecuente, ObjectId>, IRutaFrecuenteDAO {

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
        try {
            ValidationUtil.requireNonNull(id, "id");
            RutaFrecuente ruta = collection.find(Filters.eq(Constants.FIELD_ID, id)).first();
            return Optional.ofNullable(ruta);
        } catch (Exception e) {
            throw new DatabaseException("Error al buscar ruta por ID", e);
        }
    }

    @Override
    public List<RutaFrecuente> findAll() throws DatabaseException {
        try {
            return collection.find().into(new ArrayList<>());
        } catch (Exception e) {
            throw new DatabaseException("Error al obtener todos las rutas", e);
        }
    }

    @Override
    public long count() throws DatabaseException {
        try {
            return collection.countDocuments();
        } catch (Exception e) {
            throw new DatabaseException("Error al contar rutas", e);
        }
    }

    @Override
    public boolean existsById(ObjectId id) throws DatabaseException {
        try {
            ValidationUtil.requireNonNull(id, "id");
            return collection.countDocuments(Filters.eq(Constants.FIELD_ID, id)) > 0;
        } catch (Exception e) {
            throw new DatabaseException("Error al verificar existencia de ruta", e);
        }
    }

    @Override
    public List<RutaFrecuente> findByField(String fieldName, Object value) throws DatabaseException {
        try {
            ValidationUtil.requireNonEmpty(fieldName, "fieldName");
            return collection.find(Filters.eq(fieldName, value)).into(new ArrayList<>());
        } catch (Exception e) {
            throw new DatabaseException("Error al buscar ruta por campo", e);
        }
    }

    @Override
    public List<RutaFrecuente> findWithPagination(int page, int pageSize) throws DatabaseException {
        try {
            ValidationUtil.validatePositive(page, "page");
            ValidationUtil.validatePositive(pageSize, "pageSize");

            return collection.find()
                    .skip((page - 1) * pageSize)
                    .limit(pageSize)
                    .into(new ArrayList<>());
        } catch (Exception e) {
            throw new DatabaseException("Error al obtener rutas con paginación", e);
        }
    }

    @Override
    public RutaFrecuente update(RutaFrecuente entity) throws DatabaseException {
        try {
            ValidationUtil.requireNonNull(entity, "RutaFrecuente");
            ValidationUtil.requireNonNull(entity.getId(), "id");

            collection.replaceOne(Filters.eq(Constants.FIELD_ID, entity.getId()), entity);
            return entity;
        } catch (Exception e) {
            throw new DatabaseException("Error al actualizar la ruta frecuente", e);
        }
    }

    @Override
    public RutaFrecuente updatePartial(ObjectId id, Map<String, Object> updates) throws DatabaseException {
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
            throw new DatabaseException("Error al actualizar parcialmente la ruta frecuente", e);
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
    public boolean delete(RutaFrecuente entity) throws DatabaseException {
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

    // Metodos especificos que de IRutaFrecuenteDAO
 
    @Override
    public List<Parada> obtenerParadasRuta(String rutaId) throws DatabaseException {
        try {
            ValidationUtil.validateObjectId(rutaId, "viajeId");
            Optional<RutaFrecuente> rutaOpt = findById(new ObjectId(rutaId));

            if (rutaOpt.isEmpty()) {
                return Collections.emptyList();
            }

            RutaFrecuente ruta = rutaOpt.get();
            return ruta.getParadas() != null ? ruta.getParadas() : Collections.emptyList();
        } catch (DatabaseException e) {
            throw new DatabaseException("Error al obtener paradas de la ruta", e);
        }
    }

}
