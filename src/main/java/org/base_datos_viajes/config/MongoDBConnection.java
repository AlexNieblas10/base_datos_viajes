package org.base_datos_viajes.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.base_datos_viajes.exception.DatabaseException;
import org.base_datos_viajes.util.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Clase Singleton para gestionar la conexión a MongoDB.
 * Implementa el patrón Bill Pugh Singleton para asegurar thread-safety con inicialización lazy.
 * El driver de MongoDB maneja internamente un pool de conexiones.
 */
public class MongoDBConnection implements AutoCloseable {

    private final MongoClient mongoClient;
    private final MongoDatabase database;
    private final Properties properties;

    /**
     * Constructor privado - solo accesible desde el Singleton Holder.
     * Carga la configuración y establece la conexión con MongoDB.
     */
    private MongoDBConnection() {
        try {
            this.properties = loadProperties();
            this.mongoClient = createMongoClient();
            this.database = mongoClient.getDatabase(properties.getProperty(Constants.MONGODB_DATABASE_KEY));
        } catch (Exception e) {
            throw new DatabaseException(Constants.ERROR_CONNECTION_FAILED, e);
        }
    }

    /**
     * Holder estático para implementar el patrón Bill Pugh Singleton.
     * La clase interna no se carga hasta que se invoca getInstance().
     */
    private static class SingletonHolder {
        private static final MongoDBConnection INSTANCE = new MongoDBConnection();
    }

    /**
     * Obtiene la única instancia de MongoDBConnection.
     *
     * @return Instancia singleton de MongoDBConnection
     */
    public static MongoDBConnection getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * Carga las propiedades de configuración desde mongodb.properties.
     *
     * @return Properties cargadas desde el archivo
     * @throws IOException si no se puede leer el archivo
     */
    private Properties loadProperties() throws IOException {
        Properties props = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(Constants.PROPERTIES_FILE)) {
            if (input == null) {
                throw new IOException("No se encontró el archivo " + Constants.PROPERTIES_FILE);
            }
            props.load(input);
        }
        return props;
    }

    /**
     * Crea y configura el cliente de MongoDB con las propiedades cargadas.
     *
     * @return MongoClient configurado
     */
    private MongoClient createMongoClient() {
        String uri = properties.getProperty(Constants.MONGODB_URI_KEY);
        int connectionTimeout = Integer.parseInt(properties.getProperty("mongodb.connection.timeout", "10000"));
        int socketTimeout = Integer.parseInt(properties.getProperty("mongodb.socket.timeout", "5000"));
        int maxPoolSize = Integer.parseInt(properties.getProperty("mongodb.max.pool.size", "100"));
        int minPoolSize = Integer.parseInt(properties.getProperty("mongodb.min.pool.size", "10"));

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(uri))
                .applyToConnectionPoolSettings(builder ->
                        builder.maxSize(maxPoolSize)
                                .minSize(minPoolSize)
                )
                .applyToSocketSettings(builder ->
                        builder.connectTimeout(connectionTimeout, TimeUnit.MILLISECONDS)
                                .readTimeout(socketTimeout, TimeUnit.MILLISECONDS)
                )
                .build();

        return MongoClients.create(settings);
    }

    /**
     * Obtiene la base de datos configurada.
     *
     * @return Instancia de MongoDatabase
     */
    public MongoDatabase getDatabase() {
        return database;
    }

    /**
     * Obtiene el cliente de MongoDB.
     * Útil para operaciones avanzadas que requieran acceso directo al cliente.
     *
     * @return Instancia de MongoClient
     */
    public MongoClient getClient() {
        return mongoClient;
    }

    /**
     * Obtiene el nombre de la base de datos configurada.
     *
     * @return Nombre de la base de datos
     */
    public String getDatabaseName() {
        return properties.getProperty(Constants.MONGODB_DATABASE_KEY);
    }

    /**
     * Cierra la conexión con MongoDB.
     * Este método debe ser llamado cuando la aplicación se cierra.
     * Implementa AutoCloseable para uso con try-with-resources si es necesario.
     */
    @Override
    public void close() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }

    /**
     * Verifica si la conexión está activa realizando un ping a la base de datos.
     *
     * @return true si la conexión está activa, false en caso contrario
     */
    public boolean isConnected() {
        try {
            database.runCommand(new org.bson.Document("ping", 1));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
