
package org.base_datos_dao.imp;

import Entidades.Conductor;
import Entidades.Usuario;
import Entidades.Vehiculo;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.base_datos_viajes.config.MongoDBConnection;
import org.base_datos_viajes.dao.interfaces.IUsuarioDAO;
import org.base_datos_viajes.exception.DatabaseException;
import org.bson.types.ObjectId;

/**
 *
 * @author ferch
 */
public class UsuarioDAO implements IUsuarioDAO{
    
    private final MongoCollection<Usuario> coleccion;

    public UsuarioDAO() {
        //conexion a la colexion de usuarios
        this.coleccion = MongoDBConnection.getInstance().getDatabase().getCollection("usuarios", Usuario.class);
        asegurarDatosIniciales();
    }
    
    //si la coleccion esta vacia insetamos al usuario mock
    private void asegurarDatosIniciales() {
        if (coleccion.countDocuments() == 0) {
            // Datos del mock: cperez, 1234, con un vehículo
            Vehiculo vehiculoMock1 = new Vehiculo("Honda", "Civic 2020", "Blanco", "134534", "ABC-123", 4);
            Vehiculo vehiculoMock2 = new Vehiculo("Toyota", "Corolla", "Gris", "485724", "XYZ-789", 4);
            Vehiculo vehiculoMock3 = new Vehiculo("Volksvagen", "Jetta 2019", "Negro", "590185", "DEF-456", 4);

            Conductor conductorMock = new Conductor("Carlos Pérez", 100, Arrays.asList(vehiculoMock1,vehiculoMock2,vehiculoMock3));
            
            // Usuario: cperez / 1234 conductor
            Usuario usuarioMock = new Usuario("cperez", "1234", "CONDUCTOR", conductorMock);
           
            usuarioMock.setId(new ObjectId()); 
            
            coleccion.insertOne(usuarioMock);
        }
    }

    @Override
    public Usuario consultarPorCredenciales(String usuario, String contraseña) throws DatabaseException {
        try {
            return coleccion.find(Filters.and(
                    Filters.eq("usuario", usuario),
                    Filters.eq("contraseña", contraseña)
            )).first();
        } catch (Exception e) {
            throw new DatabaseException("Error al consultar credenciales: " + e.getMessage());
        }
    }

    @Override
    public Usuario save(Usuario entity) throws DatabaseException {
       try {
            if (entity.getId()== null) {
                entity.setId(new ObjectId());
            }
            coleccion.insertOne(entity);
            return entity;
        } catch (Exception e) {
            throw new DatabaseException("Error al guardar usuario: " + e.getMessage());
        }
       
    }

    @Override
    public List<Usuario> saveAll(List<Usuario> entities) throws DatabaseException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Optional<Usuario> findById(ObjectId id) throws DatabaseException {
          try {
            return Optional.ofNullable(coleccion.find(Filters.eq("_id", id)).first());
        } catch (Exception e) {
            throw new DatabaseException("Error al consultar usuario por ID: " + e.getMessage());
        }
    }

    @Override
    public List<Usuario> findAll() throws DatabaseException {
        try {
            return coleccion.find().into(new ArrayList<>());
        } catch (Exception e) {
            throw new DatabaseException("Error al consultar todos los usuarios: " + e.getMessage());
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
    public List<Usuario> findByField(String fieldName, Object value) throws DatabaseException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Usuario> findWithPagination(int page, int pageSize) throws DatabaseException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Usuario update(Usuario entity) throws DatabaseException {
        try {
            // Reemplaza el documento completo
            coleccion.replaceOne(Filters.eq("_id", entity.getId()), entity);
            return entity;
        } catch (Exception e) {
            throw new DatabaseException("Error al actualizar usuario: " + e.getMessage());
        }
    }

    @Override
    public Usuario updatePartial(ObjectId id, Map<String, Object> updates) throws DatabaseException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean deleteById(ObjectId id) throws DatabaseException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean delete(Usuario entity) throws DatabaseException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public long deleteAll() throws DatabaseException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}
