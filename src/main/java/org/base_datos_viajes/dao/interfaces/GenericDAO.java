package org.base_datos_viajes.dao.interfaces;

import org.base_datos_viajes.exception.DatabaseException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Interfaz genérica para operaciones CRUD en MongoDB.
 * Todas las implementaciones DAO deben implementar esta interfaz.
 *
 * @param <T>  Tipo de la entidad (POJO)
 * @param <ID> Tipo del identificador (generalmente String para MongoDB ObjectId)
 */
public interface GenericDAO<T, ID> {

    // ===== OPERACIONES CREATE =====

    /**
     * Guarda una nueva entidad en la base de datos.
     *
     * @param entity Entidad a guardar
     * @return Entidad guardada con su ID generado
     * @throws DatabaseException si ocurre un error durante la operación
     */
    T save(T entity) throws DatabaseException;

    /**
     * Guarda múltiples entidades en la base de datos.
     *
     * @param entities Lista de entidades a guardar
     * @return Lista de entidades guardadas con sus IDs generados
     * @throws DatabaseException si ocurre un error durante la operación
     */
    List<T> saveAll(List<T> entities) throws DatabaseException;

    // ===== OPERACIONES READ =====

    /**
     * Busca una entidad por su ID.
     *
     * @param id ID de la entidad
     * @return Optional con la entidad si existe, Optional.empty() si no existe
     * @throws DatabaseException si ocurre un error durante la operación
     */
    Optional<T> findById(ID id) throws DatabaseException;

    /**
     * Obtiene todas las entidades de la colección.
     *
     * @return Lista con todas las entidades
     * @throws DatabaseException si ocurre un error durante la operación
     */
    List<T> findAll() throws DatabaseException;

    /**
     * Cuenta el número total de entidades en la colección.
     *
     * @return Número total de documentos
     * @throws DatabaseException si ocurre un error durante la operación
     */
    long count() throws DatabaseException;

    /**
     * Verifica si existe una entidad con el ID especificado.
     *
     * @param id ID de la entidad
     * @return true si existe, false si no existe
     * @throws DatabaseException si ocurre un error durante la operación
     */
    boolean existsById(ID id) throws DatabaseException;

    /**
     * Busca entidades por un campo específico.
     *
     * @param fieldName Nombre del campo
     * @param value     Valor a buscar
     * @return Lista de entidades que coinciden
     * @throws DatabaseException si ocurre un error durante la operación
     */
    List<T> findByField(String fieldName, Object value) throws DatabaseException;

    /**
     * Obtiene entidades con paginación.
     *
     * @param page     Número de página (comienza en 0)
     * @param pageSize Tamaño de la página
     * @return Lista de entidades para la página solicitada
     * @throws DatabaseException si ocurre un error durante la operación
     */
    List<T> findWithPagination(int page, int pageSize) throws DatabaseException;

    // ===== OPERACIONES UPDATE =====

    /**
     * Actualiza una entidad completa en la base de datos.
     * La entidad debe tener su ID establecido.
     *
     * @param entity Entidad con los datos actualizados
     * @return Entidad actualizada
     * @throws DatabaseException si ocurre un error durante la operación
     */
    T update(T entity) throws DatabaseException;

    /**
     * Actualiza campos específicos de una entidad.
     * Útil para actualizaciones parciales sin necesidad de cargar toda la entidad.
     *
     * @param id      ID de la entidad a actualizar
     * @param updates Mapa con los campos y valores a actualizar
     * @return Entidad actualizada
     * @throws DatabaseException si ocurre un error durante la operación
     */
    T updatePartial(ID id, Map<String, Object> updates) throws DatabaseException;

    // ===== OPERACIONES DELETE =====

    /**
     * Elimina una entidad por su ID.
     *
     * @param id ID de la entidad a eliminar
     * @return true si se eliminó, false si no existía
     * @throws DatabaseException si ocurre un error durante la operación
     */
    boolean deleteById(ID id) throws DatabaseException;

    /**
     * Elimina una entidad específica.
     *
     * @param entity Entidad a eliminar (debe tener ID)
     * @return true si se eliminó, false si no existía
     * @throws DatabaseException si ocurre un error durante la operación
     */
    boolean delete(T entity) throws DatabaseException;

    /**
     * Elimina todas las entidades de la colección.
     * USAR CON PRECAUCIÓN - Esta operación es irreversible.
     *
     * @return Número de entidades eliminadas
     * @throws DatabaseException si ocurre un error durante la operación
     */
    long deleteAll() throws DatabaseException;
}
