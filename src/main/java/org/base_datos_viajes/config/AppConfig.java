package org.base_datos_viajes.config;

import org.base_datos_viajes.exception.DatabaseException;
import org.base_datos_viajes.util.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Clase de configuración de la aplicación.
 * Proporciona acceso centralizado a las propiedades de configuración.
 */
public class AppConfig {

    private static Properties properties;

    static {
        loadProperties();
    }

    private AppConfig() {
        throw new UnsupportedOperationException("Esta es una clase de utilidad y no puede ser instanciada");
    }

    /**
     * Carga las propiedades desde mongodb.properties.
     */
    private static void loadProperties() {
        properties = new Properties();
        try (InputStream input = AppConfig.class.getClassLoader().getResourceAsStream(Constants.PROPERTIES_FILE)) {
            if (input == null) {
                throw new DatabaseException("No se encontró el archivo " + Constants.PROPERTIES_FILE);
            }
            properties.load(input);
        } catch (IOException e) {
            throw new DatabaseException(Constants.ERROR_PROPERTIES_LOAD, e);
        }
    }

    /**
     * Obtiene el valor de una propiedad.
     *
     * @param key Clave de la propiedad
     * @return Valor de la propiedad, o null si no existe
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * Obtiene el valor de una propiedad con un valor por defecto.
     *
     * @param key          Clave de la propiedad
     * @param defaultValue Valor por defecto si la propiedad no existe
     * @return Valor de la propiedad, o defaultValue si no existe
     */
    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    /**
     * Obtiene la URI de MongoDB.
     *
     * @return URI de conexión a MongoDB
     */
    public static String getMongoDBUri() {
        return getProperty(Constants.MONGODB_URI_KEY);
    }

    /**
     * Obtiene el nombre de la base de datos.
     *
     * @return Nombre de la base de datos
     */
    public static String getDatabaseName() {
        return getProperty(Constants.MONGODB_DATABASE_KEY);
    }
}
