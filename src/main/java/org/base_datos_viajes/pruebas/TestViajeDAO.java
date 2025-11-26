package org.base_datos_viajes.pruebas;

import org.base_datos_viajes.dao.impl.ViajeDAO;
import org.base_datos_viajes.exception.DatabaseException;
import org.base_datos_viajes.model.Parada;
import org.base_datos_viajes.model.Viaje;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Clase de prueba para ViajeDAO.
 * Demuestra las operaciones CRUD y métodos específicos.
 */
public class TestViajeDAO {

    public static void main(String[] args) {
        System.out.println("========== PRUEBA DE VIAJE DAO ==========\n");

        ViajeDAO viajeDAO = new ViajeDAO();

        try {
            // 1. Crear y guardar un viaje
            System.out.println("1. Creando viaje con paradas...");
            Viaje viaje = crearViajeEjemplo();
            Viaje viajeGuardado = viajeDAO.save(viaje);
            System.out.println("✓ Viaje guardado con ID: " + viajeGuardado.getId());
            System.out.println("  Nombre: " + viajeGuardado.getNombre());
            System.out.println("  Origen: " + viajeGuardado.getOrigen());
            System.out.println("  Destino: " + viajeGuardado.getDestino());
            System.out.println("  Precio Total: $" + viajeGuardado.getPrecioTotal());
            System.out.println();

            // 2. Buscar por ID
            System.out.println("2. Buscando viaje por ID...");
            Optional<Viaje> encontrado = viajeDAO.findById(viajeGuardado.getId());
            if (encontrado.isPresent()) {
                System.out.println("✓ Viaje encontrado: " + encontrado.get().getNombre());
                System.out.println("  Fecha: " + encontrado.get().getFecha());
                System.out.println("  Hora: " + encontrado.get().getHora());
            }
            System.out.println();

            // 3. Obtener paradas del viaje
            System.out.println("3. Obteniendo paradas del viaje...");
            List<Parada> paradas = viajeDAO.obtenerParadas(viajeGuardado.getId().toHexString());
            System.out.println("✓ Paradas encontradas: " + paradas.size());
            paradas.forEach(p -> System.out.println("  - " + p.getDireccion() + " ($" + p.getPrecio() + ")"));
            System.out.println();

            // 4. Actualizar viaje
            System.out.println("4. Actualizando precio del viaje...");
            viajeGuardado.setPrecioTotal(850.0);
            Viaje actualizado = viajeDAO.update(viajeGuardado);
            System.out.println("✓ Precio actualizado a: $" + actualizado.getPrecioTotal());
            System.out.println();

            // 5. Buscar por campo
            System.out.println("5. Buscando viajes por origen...");
            List<Viaje> viajesCDMX = viajeDAO.findByField("origen", "Ciudad de México");
            System.out.println("✓ Viajes desde Ciudad de México: " + viajesCDMX.size());
            viajesCDMX.forEach(v -> System.out.println("  - " + v.getNombre() + " -> " + v.getDestino()));
            System.out.println();

            // 6. Crear múltiples viajes
            System.out.println("6. Creando múltiples viajes...");
            List<Viaje> nuevosViajes = crearViajesEjemplo();
            List<Viaje> guardados = viajeDAO.saveAll(nuevosViajes);
            System.out.println("✓ Viajes guardados: " + guardados.size());
            guardados.forEach(v -> System.out.println("  - " + v.getNombre()));
            System.out.println();

            // 7. Contar viajes
            System.out.println("7. Contando viajes...");
            long total = viajeDAO.count();
            System.out.println("✓ Total de viajes en BD: " + total);
            System.out.println();

            // 8. Paginación
            System.out.println("8. Probando paginación (página 1, 2 registros)...");
            List<Viaje> pagina = viajeDAO.findWithPagination(1, 2);
            System.out.println("✓ Viajes en página 1: " + pagina.size());
            pagina.forEach(v -> System.out.println("  - " + v.getNombre()));
            System.out.println();

            // 9. Listar todos
            System.out.println("9. Listando todos los viajes...");
            List<Viaje> todos = viajeDAO.findAll();
            System.out.println("✓ Viajes encontrados: " + todos.size());
            todos.forEach(v -> System.out.println("  - " + v.getNombre() + " (" + v.getFecha() + ")"));
            System.out.println();

            // 10. Eliminar todos
            System.out.println("10. Eliminando todos los viajes de prueba...");
            long eliminados = viajeDAO.deleteAll();
            System.out.println("✓ Viajes eliminados: " + eliminados);
            System.out.println();

            System.out.println("========== PRUEBA COMPLETADA EXITOSAMENTE ==========");

        } catch (DatabaseException e) {
            System.err.println("❌ Error en la prueba: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static Viaje crearViajeEjemplo() {
        Viaje viaje = new Viaje();
        viaje.setNombre("Viaje a Cancún");
        viaje.setOrigen("Ciudad de México");
        viaje.setDestino("Cancún");
        viaje.setFecha(LocalDate.of(2025, 8, 2));
        viaje.setHora(LocalTime.of(10, 15));
        viaje.setPrecioTotal(1200.0);

        // Agregar paradas
        List<Parada> paradas = new ArrayList<>();
        paradas.add(new Parada("Puebla Centro", 150.0));
        paradas.add(new Parada("Veracruz Puerto", 200.0));
        paradas.add(new Parada("Villahermosa Centro", 250.0));
        paradas.add(new Parada("Mérida", 300.0));
        viaje.setParadas(paradas);

        return viaje;
    }

    private static List<Viaje> crearViajesEjemplo() {
        List<Viaje> viajes = new ArrayList<>();

        // Viaje 1
        Viaje viaje1 = new Viaje();
        viaje1.setNombre("Viaje a Guadalajara");
        viaje1.setOrigen("Ciudad de México");
        viaje1.setDestino("Guadalajara");
        viaje1.setFecha(LocalDate.of(2025, 12, 20));
        viaje1.setHora(LocalTime.of(8, 0));
        viaje1.setPrecioTotal(500.0);

        List<Parada> paradas1 = new ArrayList<>();
        paradas1.add(new Parada("Querétaro Centro", 100.0));
        paradas1.add(new Parada("León", 150.0));
        viaje1.setParadas(paradas1);

        viajes.add(viaje1);

        // Viaje 2
        Viaje viaje2 = new Viaje();
        viaje2.setNombre("Viaje a Monterrey");
        viaje2.setOrigen("Ciudad de México");
        viaje2.setDestino("Monterrey");
        viaje2.setFecha(LocalDate.of(2025, 12, 5));
        viaje2.setHora(LocalTime.of(9, 0));
        viaje2.setPrecioTotal(650.0);

        List<Parada> paradas2 = new ArrayList<>();
        paradas2.add(new Parada("Querétaro", 100.0));
        paradas2.add(new Parada("San Luis Potosí", 200.0));
        paradas2.add(new Parada("Saltillo", 150.0));
        viaje2.setParadas(paradas2);

        viajes.add(viaje2);

        return viajes;
    }
}
