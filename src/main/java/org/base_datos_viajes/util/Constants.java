package org.base_datos_viajes.util;

/**
 * Clase de constantes para el módulo de persistencia.
 */
public final class Constants {

    private Constants() {
        throw new UnsupportedOperationException("Esta es una clase de utilidad y no puede ser instanciada");
    }

    // ===== CONFIGURACIÓN =====
    public static final String PROPERTIES_FILE = "mongodb.properties";
    public static final String MONGODB_URI_KEY = "mongodb.uri";
    public static final String MONGODB_DATABASE_KEY = "mongodb.database";

    // ===== NOMBRES DE COLECCIONES =====
    public static final String COLLECTION_USUARIOS = "usuarios";
    public static final String COLLECTION_CONDUCTORES = "conductores";
    public static final String COLLECTION_PASAJEROS = "pasajeros";
    public static final String COLLECTION_VIAJES = "viajes";
    public static final String COLLECTION_PARADAS = "paradas";
    public static final String COLLECTION_ADEUDOS = "adeudos";
    public static final String COLLECTION_RESERVACIONES = "reservaciones";
    public static final String COLLECTION_RUTAS = "RutasFrecuentes";

    // ===== NOMBRES DE CAMPOS COMUNES =====
    public static final String FIELD_ID = "_id";

    // ===== MENSAJES DE ERROR =====
    public static final String ERROR_CONNECTION_FAILED = "No se pudo establecer conexión con MongoDB";
    public static final String ERROR_PROPERTIES_LOAD = "Error al cargar archivo de propiedades";

    // ===== VALIDACIÓN =====
    public static final String OBJECTID_PATTERN = "^[a-fA-F0-9]{24}$";
}
