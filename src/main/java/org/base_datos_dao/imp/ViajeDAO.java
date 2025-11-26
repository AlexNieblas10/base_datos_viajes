
package org.base_datos_dao.imp;

import Entidades.Viaje;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.base_datos_viajes.config.MongoDBConnection;
import org.base_datos_viajes.dao.interfaces.IViajeDAO;
import org.base_datos_viajes.exception.DatabaseException;
import org.bson.types.ObjectId;

/**
 *
 * @author ferch
 */
public class ViajeDAO implements IViajeDAO{
    
    private final MongoCollection<Viaje> coleccion;
    
    public ViajeDAO() {
        this.coleccion = MongoDBConnection.getInstance()
                .getDatabase()
                .getCollection("viajes", Viaje.class);
    }

    @Override
    public List<Viaje> consultarPorConductor(ObjectId idConductor) throws DatabaseException {
        try {
            List<Viaje> viajes = new ArrayList<>();
            // Consulta que usa la referencia idConductor
            coleccion.find(Filters.eq("idConductor", idConductor)).into(viajes);
            return viajes;
        } catch (Exception e) {
            throw new DatabaseException("Error al consultar viajes por conductor: " + e.getMessage());
        }
    }

    @Override
    public Viaje save(Viaje entity) throws DatabaseException {
        try {
            if (entity.getId()== null) {
                entity.setId(new ObjectId());
            }
            coleccion.insertOne(entity);
            return entity;
        } catch (Exception e) {
            throw new DatabaseException("Error al guardar viaje: " + e.getMessage());
        }
    }

    @Override
    public List<Viaje> saveAll(List<Viaje> entities) throws DatabaseException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Optional<Viaje> findById(ObjectId id) throws DatabaseException {
      try {
            return Optional.ofNullable(coleccion.find(Filters.eq("_id", id)).first());
        } catch (Exception e) {
            throw new DatabaseException("Error al consultar viaje por ID: " + e.getMessage());
        }
    }

    @Override
    public List<Viaje> findAll() throws DatabaseException {
        try {
            return coleccion.find().into(new ArrayList<>());
        } catch (Exception e) {
            throw new DatabaseException("Error al consultar todos los viajes: " + e.getMessage());
        }
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
    public List<Viaje> findByField(String fieldName, Object value) throws DatabaseException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Viaje> findWithPagination(int page, int pageSize) throws DatabaseException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Viaje update(Viaje entity) throws DatabaseException {
        try {
            // asegurar de que el id exista antes de actualizar
            if (entity.getId()== null) {
                throw new DatabaseException("No se puede actualizar una entidad sin ID.");
            }
            coleccion.replaceOne(Filters.eq("_id", entity.getId()), entity);
            return entity;
        } catch (Exception e) {
            throw new DatabaseException("Error al actualizar viaje: " + e.getMessage());
        }
    }

    @Override
    public Viaje updatePartial(ObjectId id, Map<String, Object> updates) throws DatabaseException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean deleteById(ObjectId id) throws DatabaseException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean delete(Viaje entity) throws DatabaseException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public long deleteAll() throws DatabaseException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
    
}
