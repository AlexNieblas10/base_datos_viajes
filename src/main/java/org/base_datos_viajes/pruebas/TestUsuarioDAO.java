package org.base_datos_viajes.pruebas;

import org.base_datos_viajes.dao.impl.UsuarioDAO;
import org.base_datos_viajes.exception.DatabaseException;
import org.base_datos_viajes.model.Usuario;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Clase de prueba para UsuarioDAO.
 * Demuestra las operaciones CRUD básicas.
 */
public class TestUsuarioDAO {

    public static void main(String[] args) {
        System.out.println("========== PRUEBA DE USUARIO DAO ==========\n");

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        try {
            // 1. Crear y guardar un usuario
            System.out.println("1. Creando usuario...");
            Usuario usuario = new Usuario("maria_lopez", "password123");
            Usuario usuarioGuardado = usuarioDAO.save(usuario);
            System.out.println("✓ Usuario guardado con ID: " + usuarioGuardado.getId());
            System.out.println("  Usuario: " + usuarioGuardado.getUsuario());
            System.out.println();

            // 2. Buscar por ID
            System.out.println("2. Buscando usuario por ID...");
            Optional<Usuario> encontrado = usuarioDAO.findById(usuarioGuardado.getId());
            if (encontrado.isPresent()) {
                System.out.println("✓ Usuario encontrado: " + encontrado.get().getUsuario());
            }
            System.out.println();

            // 3. Verificar existencia
            System.out.println("3. Verificando existencia del usuario...");
            boolean existe = usuarioDAO.existsById(usuarioGuardado.getId());
            System.out.println("✓ Existe: " + existe);
            System.out.println();

            // 4. Actualización completa
            System.out.println("4. Actualizando usuario completo...");
            usuarioGuardado.setContraseña("newPassword456");
            Usuario actualizado = usuarioDAO.update(usuarioGuardado);
            System.out.println("✓ Usuario actualizado");
            System.out.println();

            // 5. Actualización parcial
            System.out.println("5. Actualizando parcialmente (solo usuario)...");
            Map<String, Object> updates = new HashMap<>();
            updates.put("usuario", "maria_lopez_updated");
            Usuario parcialmenteActualizado = usuarioDAO.updatePartial(usuarioGuardado.getId(), updates);
            System.out.println("✓ Usuario actualizado parcialmente: " + parcialmenteActualizado.getUsuario());
            System.out.println();

            // 6. Buscar por campo
            System.out.println("6. Buscando por campo 'usuario'...");
            List<Usuario> usuarios = usuarioDAO.findByField("usuario", "maria_lopez_updated");
            System.out.println("✓ Usuarios encontrados: " + usuarios.size());
            usuarios.forEach(u -> System.out.println("  - " + u.getUsuario()));
            System.out.println();

            // 7. Guardar múltiples usuarios
            System.out.println("7. Guardando múltiples usuarios...");
            List<Usuario> nuevosUsuarios = List.of(
                new Usuario("carlos_garcia", "pass1"),
                new Usuario("ana_martinez", "pass2"),
                new Usuario("pedro_sanchez", "pass3")
            );
            List<Usuario> guardados = usuarioDAO.saveAll(nuevosUsuarios);
            System.out.println("✓ Usuarios guardados: " + guardados.size());
            System.out.println();

            // 8. Contar usuarios
            System.out.println("8. Contando usuarios...");
            long total = usuarioDAO.count();
            System.out.println("✓ Total de usuarios en BD: " + total);
            System.out.println();

            // 9. Paginación
            System.out.println("9. Probando paginación (página 1, 2 registros)...");
            List<Usuario> pagina = usuarioDAO.findWithPagination(1, 2);
            System.out.println("✓ Usuarios en página 1: " + pagina.size());
            pagina.forEach(u -> System.out.println("  - " + u.getUsuario()));
            System.out.println();

            // 10. Listar todos
            System.out.println("10. Listando todos los usuarios...");
            List<Usuario> todos = usuarioDAO.findAll();
            System.out.println("✓ Usuarios encontrados: " + todos.size());
            todos.forEach(u -> System.out.println("  - " + u.getUsuario()));
            System.out.println();

            // 11. Eliminar todos
            System.out.println("11. Eliminando todos los usuarios de prueba...");
            long eliminados = usuarioDAO.deleteAll();
            System.out.println("✓ Usuarios eliminados: " + eliminados);
            System.out.println();

            System.out.println("========== PRUEBA COMPLETADA EXITOSAMENTE ==========");

        } catch (DatabaseException e) {
            System.err.println("❌ Error en la prueba: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
