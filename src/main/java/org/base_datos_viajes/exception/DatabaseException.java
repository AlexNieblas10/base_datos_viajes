package org.base_datos_viajes.exception;

/**
 * Excepción personalizada para errores relacionados con operaciones de base de datos.
 * Envuelve excepciones de MongoDB y proporciona mensajes significativos.
 */
public class DatabaseException extends RuntimeException {

    /**
     * Constructor con mensaje.
     *
     * @param message Descripción del error
     */
    public DatabaseException(String message) {
        super(message);
    }

    /**
     * Constructor con mensaje y causa.
     *
     * @param message Descripción del error
     * @param cause   Excepción original que causó el error
     */
    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor con causa.
     *
     * @param cause Excepción original que causó el error
     */
    public DatabaseException(Throwable cause) {
        super(cause);
    }
}
