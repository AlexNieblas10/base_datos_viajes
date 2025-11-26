package org.base_datos_viajes.exception;

/**
 * Excepción personalizada para errores de validación de datos.
 * Se lanza cuando los datos no cumplen con las reglas de validación.
 */
public class ValidationException extends RuntimeException {

    private String fieldName;

    /**
     * Constructor con mensaje.
     *
     * @param message Descripción del error de validación
     */
    public ValidationException(String message) {
        super(message);
    }

    /**
     * Constructor con mensaje y nombre de campo.
     *
     * @param message   Descripción del error de validación
     * @param fieldName Nombre del campo que falló la validación
     */
    public ValidationException(String message, String fieldName) {
        super(message);
        this.fieldName = fieldName;
    }

    /**
     * Constructor con mensaje y causa.
     *
     * @param message Descripción del error de validación
     * @param cause   Excepción original que causó el error
     */
    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Obtiene el nombre del campo que falló la validación.
     *
     * @return Nombre del campo, o null si no se especificó
     */
    public String getFieldName() {
        return fieldName;
    }
}
