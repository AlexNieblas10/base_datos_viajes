package org.base_datos_viajes.util;

import org.base_datos_viajes.exception.ValidationException;
import org.bson.types.ObjectId;

import java.util.Collection;
import java.util.regex.Pattern;

/**
 * Utilidades de validación para datos del sistema.
 * Proporciona métodos comunes de validación con mensajes de error claros.
 */
public final class ValidationUtil {

    private static final Pattern OBJECTID_PATTERN = Pattern.compile(Constants.OBJECTID_PATTERN);

    private ValidationUtil() {
        throw new UnsupportedOperationException("Esta es una clase de utilidad y no puede ser instanciada");
    }

    /**
     * Valida que un objeto no sea nulo.
     *
     * @param obj       Objeto a validar
     * @param fieldName Nombre del campo (para mensaje de error)
     * @throws ValidationException si el objeto es nulo
     */
    public static void requireNonNull(Object obj, String fieldName) {
        if (obj == null) {
            throw new ValidationException("El campo '" + fieldName + "' no puede ser nulo", fieldName);
        }
    }

    /**
     * Valida que una cadena no sea nula ni vacía.
     *
     * @param str       Cadena a validar
     * @param fieldName Nombre del campo (para mensaje de error)
     * @throws ValidationException si la cadena es nula o vacía
     */
    public static void requireNonEmpty(String str, String fieldName) {
        requireNonNull(str, fieldName);
        if (str.trim().isEmpty()) {
            throw new ValidationException("El campo '" + fieldName + "' no puede estar vacío", fieldName);
        }
    }

    /**
     * Valida que una colección no sea nula ni vacía.
     *
     * @param collection Colección a validar
     * @param fieldName  Nombre del campo (para mensaje de error)
     * @throws ValidationException si la colección es nula o vacía
     */
    public static void requireNonEmpty(Collection<?> collection, String fieldName) {
        requireNonNull(collection, fieldName);
        if (collection.isEmpty()) {
            throw new ValidationException("La colección '" + fieldName + "' no puede estar vacía", fieldName);
        }
    }

    /**
     * Valida la longitud de una cadena.
     *
     * @param str       Cadena a validar
     * @param minLength Longitud mínima permitida
     * @param maxLength Longitud máxima permitida
     * @param fieldName Nombre del campo (para mensaje de error)
     * @throws ValidationException si la longitud está fuera del rango
     */
    public static void validateStringLength(String str, int minLength, int maxLength, String fieldName) {
        requireNonNull(str, fieldName);
        int length = str.trim().length();
        if (length < minLength || length > maxLength) {
            throw new ValidationException(
                    String.format("El campo '%s' debe tener entre %d y %d caracteres, tiene %d",
                            fieldName, minLength, maxLength, length),
                    fieldName
            );
        }
    }

    /**
     * Valida que una cadena coincida con un patrón regex.
     *
     * @param str       Cadena a validar
     * @param pattern   Patrón regex
     * @param fieldName Nombre del campo (para mensaje de error)
     * @throws ValidationException si la cadena no coincide con el patrón
     */
    public static void validatePattern(String str, Pattern pattern, String fieldName) {
        requireNonEmpty(str, fieldName);
        if (!pattern.matcher(str).matches()) {
            throw new ValidationException(
                    "El campo '" + fieldName + "' no cumple con el formato esperado",
                    fieldName
            );
        }
    }

    /**
     * Valida que un String sea un ObjectId válido de MongoDB.
     *
     * @param id        String a validar
     * @param fieldName Nombre del campo (para mensaje de error)
     * @throws ValidationException si no es un ObjectId válido
     */
    public static void validateObjectId(String id, String fieldName) {
        requireNonEmpty(id, fieldName);
        if (!ObjectId.isValid(id)) {
            throw new ValidationException(
                    "El campo '" + fieldName + "' no es un ObjectId válido de MongoDB",
                    fieldName
            );
        }
    }

    /**
     * Valida que un número esté dentro de un rango.
     *
     * @param value     Valor a validar
     * @param min       Valor mínimo permitido (inclusivo)
     * @param max       Valor máximo permitido (inclusivo)
     * @param fieldName Nombre del campo (para mensaje de error)
     * @throws ValidationException si el valor está fuera del rango
     */
    public static void validateRange(int value, int min, int max, String fieldName) {
        if (value < min || value > max) {
            throw new ValidationException(
                    String.format("El campo '%s' debe estar entre %d y %d, valor actual: %d",
                            fieldName, min, max, value),
                    fieldName
            );
        }
    }

    /**
     * Valida que un número sea positivo.
     *
     * @param value     Valor a validar
     * @param fieldName Nombre del campo (para mensaje de error)
     * @throws ValidationException si el valor no es positivo
     */
    public static void validatePositive(int value, String fieldName) {
        if (value <= 0) {
            throw new ValidationException(
                    "El campo '" + fieldName + "' debe ser un número positivo, valor actual: " + value,
                    fieldName
            );
        }
    }

    /**
     * Valida que un número sea no negativo (mayor o igual a cero).
     *
     * @param value     Valor a validar
     * @param fieldName Nombre del campo (para mensaje de error)
     * @throws ValidationException si el valor es negativo
     */
    public static void validateNonNegative(int value, String fieldName) {
        if (value < 0) {
            throw new ValidationException(
                    "El campo '" + fieldName + "' no puede ser negativo, valor actual: " + value,
                    fieldName
            );
        }
    }
}
