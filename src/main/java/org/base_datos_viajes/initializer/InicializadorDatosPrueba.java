package org.base_datos_viajes.initializer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.base_datos_viajes.dao.impl.ConductorDAO;
import org.base_datos_viajes.dao.impl.UsuarioDAO;
import org.base_datos_viajes.dao.impl.ViajeDAO;
import org.base_datos_viajes.exception.DatabaseException;
import org.base_datos_viajes.exception.ValidationException;
import org.base_datos_viajes.model.Conductor;
import org.base_datos_viajes.model.Parada;
import org.base_datos_viajes.model.Usuario;
import org.base_datos_viajes.model.Vehiculo;
import org.base_datos_viajes.model.Viaje;
import org.bson.types.ObjectId;
import java.util.Optional;
import org.base_datos_viajes.dao.impl.ParadaDAO;
import org.base_datos_viajes.dao.impl.PasajeroDAO;
import org.base_datos_viajes.dao.impl.RutasFrecuentesDAO;
import org.base_datos_viajes.model.Pasajero;
import org.base_datos_viajes.model.RutaFrecuente;

/**
 *
 * @author ferch calse de para la inicializacion de datos de prueba en la base
 * de datos de monogo
 */
public class InicializadorDatosPrueba {

    private static final Logger LOGGER = Logger.getLogger(InicializadorDatosPrueba.class.getName());

    private final UsuarioDAO usuarioDAO;
    private final ConductorDAO conductorDAO;
    private final PasajeroDAO pasajeroDAO;
    private final ParadaDAO paradaDAO;
    private final ViajeDAO viajeDAO;
    private final RutasFrecuentesDAO rutaDAO;

    public InicializadorDatosPrueba() {
        this.usuarioDAO = new UsuarioDAO();
        this.conductorDAO = new ConductorDAO();
        this.viajeDAO = new ViajeDAO();
        this.pasajeroDAO = new PasajeroDAO();
        this.paradaDAO = new ParadaDAO();
        this.rutaDAO = new RutasFrecuentesDAO();
    }

    /**
     * Agrega datos de prueba a la base de datos de MongoDB.
     */
    private void agregarDatosPrueba() {
        LOGGER.info("Iniciando insercion de datos de prueba...");
        try {
            // Crear Vehículos (Usando el constructor de Vehiculo.java)
            // Constructor: Vehiculo(ObjectId id, String marca, String modelo, String color, String numSerie, String placas, int capacidad)
            // --- VEHÍCULO 1: Honda Civic 2020 ---
            Vehiculo vehiculo1 = new Vehiculo(); 
            vehiculo1.setMarca("Honda");
            vehiculo1.setModelo("Civic 2020");
            vehiculo1.setColor("Blanco");
            vehiculo1.setNumeroSerie("H2020BS"); 
            vehiculo1.setPlacas("ABC-123");
            vehiculo1.setCapacidad(4);
            vehiculo1.setId(new ObjectId()); 

            // --- VEHÍCULO 2: Toyota Corolla 2021 ---
            Vehiculo vehiculo2 = new Vehiculo();
            vehiculo2.setMarca("Toyota");
            vehiculo2.setModelo("Corolla 2021");
            vehiculo2.setColor("Gris");
            vehiculo2.setNumeroSerie("T2021GS"); 
            vehiculo2.setPlacas("XYZ-789");
            vehiculo2.setCapacidad(4);
            vehiculo2.setId(new ObjectId());

            // --- VEHÍCULO 3: Volkswagen Jetta 2019 ---
            Vehiculo vehiculo3 = new Vehiculo();
            vehiculo3.setMarca("Volkswagen");
            vehiculo3.setModelo("Jetta 2019");
            vehiculo3.setColor("Negro");
            vehiculo3.setNumeroSerie("V2019NS"); 
            vehiculo3.setPlacas("DEF-456");
            vehiculo3.setCapacidad(4);
            vehiculo3.setId(new ObjectId());

            List<Vehiculo> vehiculos = Arrays.asList(vehiculo1, vehiculo2, vehiculo3);

            // Crear y persistir Conductor
            Conductor conductor1 = new Conductor("Carlos Perez", 100.0, vehiculos);

            conductorDAO.save(conductor1);
            LOGGER.log(Level.INFO, "Conductor de prueba insertado con ID: {0}", conductor1.getId());

            // Los ids de los vehículos se generan al guardar el conductor. Debemos recuperarlos.
            Optional<Conductor> optionalConductor = conductorDAO.findById(conductor1.getId());

            // Obtener el objeto Conductor o lanzar una excepcion si falla la busqueda
            Conductor conductorConIDs = optionalConductor.orElseThrow(()
                    -> new DatabaseException("Error: El conductor no fue encontrado despues de la insercion.")
            );
            // Obtenemos los vehiculos con sus ObjectIds generados
            Vehiculo vehiculoConID1 = conductorConIDs.getVehiculos().get(0);
            Vehiculo vehiculoConID2 = conductorConIDs.getVehiculos().get(1);

            ObjectId vehiculoId1 = vehiculoConID1.getId();
            ObjectId vehiculoId2 = vehiculoConID2.getId();

            Pasajero pasajero1 = new Pasajero("Carlos Perez");
            pasajeroDAO.save(pasajero1);

            // 3. Crear y persistir Usuario vinculado al Conductor
            Usuario usuario1 = new Usuario("cperez", "1234");
            usuario1.setConductorId(conductor1.getId());
            usuario1.setPasajeroId(pasajero1.getId());
            usuarioDAO.save(usuario1);
            LOGGER.log(Level.INFO, "Usuario de prueba (cperez) insertado con ID: {0}", usuario1.getId());

            // 4. Crear Paradas y Viajes
            // viaje1: Obregon a Navojoa ---
            LocalDate fechaViaje1 = LocalDate.now();
            LocalTime horaViaje1 = LocalTime.of(13, 0);
            
            // Constructor Parada: Parada(String direccion, double precio)
            Parada p2v1 = new Parada("Tutuli", 50.0);
            Parada p3v1 = new Parada("ITSON", 30.0);
            List<Parada> paradasViaje1 = new ArrayList<>();
            paradasViaje1.add(p2v1);
            paradasViaje1.add(p3v1);

            Viaje viaje1 = new Viaje(
                    "Viaje a Navojoa",
                    "Navojoa",
                    "Obregon",
                    fechaViaje1,
                    horaViaje1,
                    250.0
            );

            viaje1.setConductorId(conductor1.getId());
            viaje1.setVehiculoId(vehiculoId1);
            viaje1.setParadas(paradasViaje1);
            viaje1.setEstaActivo(true);
            viaje1.setCantidadPasajeros(2);  // 2 pasajeros de prueba
            viajeDAO.save(viaje1);
            LOGGER.log(Level.INFO, "Viaje de prueba 1 insertado con ID: {0}", viaje1.getId());

            // viaje 2: Obregon a Esperanza ---
            LocalDate fechaViaje2 = LocalDate.now().plusDays(5);
            LocalTime horaViaje2 = LocalTime.of(14, 0);
            Parada pv2 = new Parada("Central Camiones", 40.0);
            List<Parada> paradasViaje2 = new ArrayList<>();
            paradasViaje2.add(pv2);

            Viaje viaje2 = new Viaje(
                    "Viaje a Esperanza",
                    "Esperanza",
                    "Obregon",
                    fechaViaje2,
                    horaViaje2,
                    70.0
            );

            viaje2.setConductorId(conductor1.getId());
            viaje2.setVehiculoId(vehiculoId2);
            viaje2.setParadas(paradasViaje2);
            viaje2.setEstaActivo(true);
            viaje2.setCantidadPasajeros(3);  // 3 pasajeros de prueba
            viajeDAO.save(viaje2);
            LOGGER.log(Level.INFO, "Viaje de prueba 2 insertado con ID: {0}", viaje2.getId());

            LOGGER.info("Insercion de datos de prueba finalizada con exito.");

            RutaFrecuente ruta1 = new RutaFrecuente();
            rutaDAO.save(ruta1);

        } catch (DatabaseException | ValidationException e) {
            LOGGER.log(Level.SEVERE, "Error al insertar datos de prueba. Asegurate de que MongoDB este corriendo y la conexion sea correcta: " + e.getMessage(), e);
        }
    }

    public static void inicializarSiEsNecesario() {
        InicializadorDatosPrueba initializer = new InicializadorDatosPrueba();
        try {
            if (initializer.usuarioDAO.count() == 0) {
                initializer.agregarDatosPrueba();
            } else {
                LOGGER.info("La base de datos ya contiene datos. Inicialización omitida.");
            }
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Error durante la verificación/inicialización de la BD.", e);
        }
    }

    public static void main(String[] args) {
        inicializarSiEsNecesario();
    }
}
