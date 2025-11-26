package org.base_datos_viajes.pruebas;

import org.base_datos_viajes.dao.impl.ConductorDAO;
import org.base_datos_viajes.exception.DatabaseException;
import org.base_datos_viajes.model.Conductor;
import org.base_datos_viajes.model.Vehiculo;
import org.base_datos_viajes.model.Viaje;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Clase de prueba para ConductorDAO.
 * Demuestra las operaciones CRUD y métodos específicos.
 */
public class TestConductorDAO {

    public static void main(String[] args) {
        System.out.println("========== PRUEBA DE CONDUCTOR DAO ==========\n");

        ConductorDAO conductorDAO = new ConductorDAO();

        try {
            // 1. Crear y guardar un conductor
            System.out.println("1. Creando conductor...");
            Conductor conductor = crearConductorEjemplo();
            Conductor conductorGuardado = conductorDAO.save(conductor);
            System.out.println("✓ Conductor guardado con ID: " + conductorGuardado.getId());
            System.out.println("  Nombre: " + conductorGuardado.getNombre());
            System.out.println("  Calificación: " + conductorGuardado.getCalificacion());
            System.out.println();

            // 2. Buscar por ID
            System.out.println("2. Buscando conductor por ID...");
            Optional<Conductor> encontrado = conductorDAO.findById(conductorGuardado.getId());
            if (encontrado.isPresent()) {
                System.out.println("✓ Conductor encontrado: " + encontrado.get().getNombre());
            }
            System.out.println();

            // 3. Obtener viajes del conductor
            System.out.println("3. Obteniendo viajes del conductor...");
            List<Viaje> viajes = conductorDAO.obtenerViajes(conductorGuardado.getId().toHexString());
            System.out.println("✓ Viajes encontrados: " + viajes.size());
            viajes.forEach(v -> System.out.println("  - Viaje: " + v.getNombre()));
            System.out.println();

            // 4. Obtener vehículos del conductor
            System.out.println("4. Obteniendo vehículos del conductor...");
            List<Vehiculo> vehiculos = conductorDAO.obtenerVehiculos(conductorGuardado.getId().toHexString());
            System.out.println("✓ Vehículos encontrados: " + vehiculos.size());
            vehiculos.forEach(v -> System.out.println("  - " + v.getMarca() + " " + v.getModelo()));
            System.out.println();

            // 5. Actualizar conductor
            System.out.println("5. Actualizando calificación del conductor...");
            conductorGuardado.setCalificacion(4.9);
            Conductor actualizado = conductorDAO.update(conductorGuardado);
            System.out.println("✓ Calificación actualizada a: " + actualizado.getCalificacion());
            System.out.println();

            // 6. Contar conductores
            System.out.println("6. Contando conductores...");
            long total = conductorDAO.count();
            System.out.println("✓ Total de conductores en BD: " + total);
            System.out.println();

            // 7. Listar todos
            System.out.println("7. Listando todos los conductores...");
            List<Conductor> todos = conductorDAO.findAll();
            System.out.println("✓ Conductores encontrados: " + todos.size());
            todos.forEach(c -> System.out.println("  - " + c.getNombre() + " (Calificación: " + c.getCalificacion() + ")"));
            System.out.println();

            // 8. Eliminar conductor
            System.out.println("8. Eliminando conductor...");
            boolean eliminado = conductorDAO.deleteById(conductorGuardado.getId());
            System.out.println("✓ Conductor eliminado: " + eliminado);
            System.out.println();

            System.out.println("========== PRUEBA COMPLETADA EXITOSAMENTE ==========");

        } catch (DatabaseException e) {
            System.err.println("❌ Error en la prueba: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static Conductor crearConductorEjemplo() {
        Conductor conductor = new Conductor();
        conductor.setNombre("Juan Pérez");
        conductor.setCalificacion(4.5);

        // Agregar vehículos
        List<Vehiculo> vehiculos = new ArrayList<>();
        Vehiculo vehiculo1 = new Vehiculo("Toyota", "Corolla", "Blanco", "ABC123", "XYZ789", 4);
        Vehiculo vehiculo2 = new Vehiculo("Honda", "Civic", "Negro", "DEF456", "UVW012", 4);
        vehiculos.add(vehiculo1);
        vehiculos.add(vehiculo2);
        conductor.setVehiculos(vehiculos);

        // Agregar viajes
        List<Viaje> viajes = new ArrayList<>();
        Viaje viaje1 = new Viaje();
        viaje1.setId(new ObjectId());
        viaje1.setNombre("Viaje a Guadalajara");
        viaje1.setOrigen("Ciudad de México");
        viaje1.setDestino("Guadalajara");
        viaje1.setFecha(LocalDate.of(2025, 12, 1));
        viaje1.setHora(LocalTime.of(8, 0));
        viaje1.setPrecioTotal(500.0);

        Viaje viaje2 = new Viaje();
        viaje2.setId(new ObjectId());
        viaje2.setNombre("Viaje a Monterrey");
        viaje2.setOrigen("Ciudad de México");
        viaje2.setDestino("Monterrey");
        viaje2.setFecha(LocalDate.of(2025, 12, 5));
        viaje2.setHora(LocalTime.of(10, 0));
        viaje2.setPrecioTotal(700.0);

        viajes.add(viaje1);
        viajes.add(viaje2);
        conductor.setViajes(viajes);

        return conductor;
    }
}
