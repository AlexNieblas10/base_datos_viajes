package org.base_datos_viajes.dao.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import org.base_datos_viajes.config.MongoDBConnection;
import org.base_datos_viajes.exception.DatabaseException;
import org.base_datos_viajes.model.Usuario;
import org.base_datos_viajes.util.Constants;
import org.base_datos_viajes.util.ValidationUtil;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.base_datos_viajes.dao.interfaces.IGenericDAO;

/**
 * DAO para la entidad Usuario.
 * Implementa todas las operaciones CRUD definidas en GenericDAO.
 */
public class UsuarioDAO implements IGenericDAO<Usuario, ObjectId> {

    private final MongoCollection<Usuario> collection;

    public UsuarioDAO() {
        this.collection = MongoDBConnection.getInstance()
                .getDatabase()
                .getCollection(Constants.COLLECTION_USUARIOS, Usuario.class);
    }

    @Override
    public Usuario save(Usuario entity) throws DatabaseException {
        try {
            ValidationUtil.requireNonNull(entity, "usuario");
            if (entity.getId() == null) {
                entity.setId(new ObjectId());
            }
            collection.insertOne(entity);
            return entity;
        } catch (Exception e) {
            throw new DatabaseException("Error al guardar usuario", e);
        }
    }

    @Override
    public List<Usuario> saveAll(List<Usuario> entities) throws DatabaseException {
        try {
            ValidationUtil.requireNonEmpty(entities, "usuarios");
            for (Usuario usuario : entities) {
                if (usuario.getId() == null) {
                    usuario.setId(new ObjectId());
                }
            }
            collection.insertMany(entities);
            return entities;
        } catch (Exception e) {
            throw new DatabaseException("Error al guardar múltiples usuarios", e);
        }
    }

    @Override
    public Optional<Usuario> findById(ObjectId id) throws DatabaseException {
        try {
            ValidationUtil.requireNonNull(id, "id");
            Usuario usuario = collection.find(Filters.eq(Constants.FIELD_ID, id)).first();
            return Optional.ofNullable(usuario);
        } catch (Exception e) {
            throw new DatabaseException("Error al buscar usuario por ID", e);
        }
    }

    @Override
    public List<Usuario> findAll() throws DatabaseException {
        try {
            return collection.find().into(new ArrayList<>());
        } catch (Exception e) {
            throw new DatabaseException("Error al obtener todos los usuarios", e);
        }
    }

    @Override
    public long count() throws DatabaseException {
        try {
            return collection.countDocuments();
        } catch (Exception e) {
            throw new DatabaseException("Error al contar usuarios", e);
        }
    }

    @Override
    public boolean existsById(ObjectId id) throws DatabaseException {
        try {
            ValidationUtil.requireNonNull(id, "id");
            return collection.countDocuments(Filters.eq(Constants.FIELD_ID, id)) > 0;
        } catch (Exception e) {
            throw new DatabaseException("Error al verificar existencia de usuario", e);
        }
    }

    @Override
    public Usuario update(Usuario entity) throws DatabaseException {
        try {
            ValidationUtil.requireNonNull(entity, "usuario");
            ValidationUtil.requireNonNull(entity.getId(), "id");

            collection.replaceOne(Filters.eq(Constants.FIELD_ID, entity.getId()), entity);
            return entity;
        } catch (Exception e) {
            throw new DatabaseException("Error al actualizar usuario", e);
        }
    }

    @Override
    public Usuario updatePartial(ObjectId id, Map<String, Object> updates) throws DatabaseException {
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
            throw new DatabaseException("Error al actualizar parcialmente usuario", e);
        }
    }

    @Override
    public boolean deleteById(ObjectId id) throws DatabaseException {
        try {
            ValidationUtil.requireNonNull(id, "id");
            DeleteResult result = collection.deleteOne(Filters.eq(Constants.FIELD_ID, id));
            return result.getDeletedCount() > 0;
        } catch (Exception e) {
            throw new DatabaseException("Error al eliminar usuario", e);
        }
    }

    @Override
    public boolean delete(Usuario entity) throws DatabaseException {
        try {
            ValidationUtil.requireNonNull(entity, "usuario");
            ValidationUtil.requireNonNull(entity.getId(), "id");
            return deleteById(entity.getId());
        } catch (DatabaseException e) {
            throw new DatabaseException("Error al eliminar usuario", e);
        }
    }

    @Override
    public long deleteAll() throws DatabaseException {
        try {
            DeleteResult result = collection.deleteMany(Filters.empty());
            return result.getDeletedCount();
        } catch (Exception e) {
            throw new DatabaseException("Error al eliminar todos los usuarios", e);
        }
    }

    @Override
    public List<Usuario> findByField(String fieldName, Object value) throws DatabaseException {
        try {
            ValidationUtil.requireNonEmpty(fieldName, "fieldName");
            return collection.find(Filters.eq(fieldName, value)).into(new ArrayList<>());
        } catch (Exception e) {
            throw new DatabaseException("Error al buscar usuarios por campo", e);
        }
    }

    @Override
    public List<Usuario> findWithPagination(int page, int pageSize) throws DatabaseException {
        try {
            ValidationUtil.validatePositive(page, "page");
            ValidationUtil.validatePositive(pageSize, "pageSize");

            return collection.find()
                    .skip((page - 1) * pageSize)
                    .limit(pageSize)
                    .into(new ArrayList<>());
        } catch (Exception e) {
            throw new DatabaseException("Error al obtener usuarios con paginación", e);
        }
    }

    //buscar por usuario y contraseña
    public Optional<Usuario> consultarPorCredenciales(String usuario, String contrasena) throws DatabaseException {
        try {
            Bson filtro = Filters.and(
                    Filters.eq("usuario", usuario),
                    Filters.eq("contraseña", contrasena)
            );

            Usuario entidad = collection.find(filtro).first();
            return Optional.ofNullable(entidad);

        } catch (Exception e) {
            throw new DatabaseException("Error al consultar usuario por credenciales", e);
        }
    }

}
