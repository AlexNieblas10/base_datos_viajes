package org.base_datos_viajes.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Utilidad para el cifrado y validación de contraseñas usando BCrypt.
 * Esta clase proporciona métodos estáticos para hashear contraseñas de forma segura
 * y verificar contraseñas en texto plano contra sus hashes.
 */
public class PasswordUtil {

    /**
     * Factor de complejidad para BCrypt.
     * Un valor de 12 proporciona un buen balance entre seguridad y rendimiento.
     */
    private static final int BCRYPT_ROUNDS = 12;

    /**
     * Constructor privado para prevenir instanciación.
     */
    private PasswordUtil() {
        throw new UnsupportedOperationException("Clase de utilidad no puede ser instanciada");
    }

    /**
     * Hashea una contraseña usando BCrypt.
     *
     * @param plainPassword La contraseña en texto plano a hashear
     * @return El hash BCrypt de la contraseña
     * @throws IllegalArgumentException si la contraseña es null o vacía
     */
    public static String hashPassword(String plainPassword) {
        if (plainPassword == null || plainPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede ser null o vacía");
        }
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(BCRYPT_ROUNDS));
    }

    /**
     * Verifica si una contraseña en texto plano coincide con un hash BCrypt.
     *
     * @param plainPassword La contraseña en texto plano a verificar
     * @param hashedPassword El hash BCrypt contra el cual verificar
     * @return true si la contraseña coincide con el hash, false en caso contrario
     */
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        if (plainPassword == null || hashedPassword == null) {
            return false;
        }
        try {
            return BCrypt.checkpw(plainPassword, hashedPassword);
        } catch (IllegalArgumentException e) {
            // El hash no es válido
            return false;
        }
    }

    /**
     * Verifica si un string es un hash BCrypt válido.
     *
     * @param hash El string a verificar
     * @return true si es un hash BCrypt válido, false en caso contrario
     */
    public static boolean isBCryptHash(String hash) {
        if (hash == null) {
            return false;
        }
        // Los hashes BCrypt siempre comienzan con $2a$, $2b$, o $2y$
        return hash.matches("^\\$2[aby]\\$\\d{2}\\$.{53}$");
    }
}
