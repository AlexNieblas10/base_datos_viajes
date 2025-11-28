
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.base_datos_viajes.config.AppConfig;
import org.base_datos_viajes.dao.impl.ConductorDAO;
import org.base_datos_viajes.dao.impl.UsuarioDAO;
import org.base_datos_viajes.dao.impl.ViajeDAO;
import org.base_datos_viajes.dao.interfaces.GenericDAO;
import org.base_datos_viajes.dao.interfaces.IConductorDAO;
import org.base_datos_viajes.dao.interfaces.IViajeDAO;
import org.base_datos_viajes.exception.DatabaseException;
import org.base_datos_viajes.exception.ValidationException;
import org.base_datos_viajes.model.Conductor;
import org.base_datos_viajes.model.Parada;
import org.base_datos_viajes.model.Usuario;
import org.base_datos_viajes.model.Vehiculo;
import org.base_datos_viajes.model.Viaje;
import org.bson.types.ObjectId;
import java.util.Optional;


/**
 *
 * @author ferch
 * calse de para la inicializacion de datos de prueba en la base de datos de monogo
 */
public class InicializadorDatosPrueba {
   private static final Logger LOGGER = Logger.getLogger(InicializadorDatosPrueba.class.getName());

    private final UsuarioDAO usuarioDAO;
    private final ConductorDAO conductorDAO;
    private final ViajeDAO viajeDAO;
    
   public InicializadorDatosPrueba() {
        this.usuarioDAO = new UsuarioDAO(); 
        this.conductorDAO = new ConductorDAO();
        this.viajeDAO = new ViajeDAO();
    }
    
    /**
     * Agrega datos de prueba a la base de datos de MongoDB.
     */
   public void agregarDatosPrueba() {
        LOGGER.info("Iniciando insercion de datos de prueba...");
        try {
            // Crear Vehículos (Usando el constructor de Vehiculo.java)
            // Constructor: Vehiculo(ObjectId id, String marca, String modelo, String color, String numSerie, String placas, int capacidad)
            Vehiculo vehiculo1 = new Vehiculo("Honda", "Civic 2020", "Blanco", "H2020B", "ABC-123", 4);
            vehiculo1.setId(new ObjectId());
            Vehiculo vehiculo2 = new Vehiculo("Toyota", "Corolla 2021", "Gris", "T2021G", "XYZ-789", 4);
            vehiculo2.setId(new ObjectId());
            Vehiculo vehiculo3 = new Vehiculo("Volkswagen", "Jetta 2019", "Negro", "V2019N", "DEF-456", 4);
            vehiculo3.setId(new ObjectId());
            
            List<Vehiculo> vehiculos = Arrays.asList(vehiculo1, vehiculo2, vehiculo3);
            List<Viaje> viajesIniciales = new ArrayList<>();

            // Crear y persistir Conductor
  
            Conductor conductor1 = new Conductor("Carlos Perez", 100.0, vehiculos);
            
             conductorDAO.save(conductor1);
            LOGGER.log(Level.INFO, "Conductor de prueba insertado con ID: {0}", conductor1.getId());

            // Los ids de los vehículos se generan al guardar el conductor. Debemos recuperarlos.
            Optional<Conductor> optionalConductor = conductorDAO.findById(conductor1.getId());
            
            // Obtener el objeto Conductor o lanzar una excepcion si falla la busqueda
            Conductor conductorConIDs = optionalConductor.orElseThrow(() -> 
                new DatabaseException("Error: El conductor no fue encontrado despues de la insercion.")
            );
            // Obtenemos los vehiculos con sus ObjectIds generados
            Vehiculo vehiculoConID1 = conductorConIDs.getVehiculos().get(0); 
            Vehiculo vehiculoConID2 = conductorConIDs.getVehiculos().get(1);
            
            ObjectId vehiculoId1 = vehiculoConID1.getId();
            ObjectId vehiculoId2 = vehiculoConID2.getId();

            // 3. Crear y persistir Usuario vinculado al Conductor
            Usuario usuario1 = new Usuario("cperez", "1234");
            usuario1.setConductorId(conductor1.getId());
            usuarioDAO.save(usuario1);
            LOGGER.log(Level.INFO, "Usuario de prueba (cperez) insertado con ID: {0}", usuario1.getId());

            // 4. Crear Paradas y Viajes
            
            // viaje1: Obregon a Navojoa ---
            LocalDate fechaViaje1 = LocalDate.now().plusDays(1);
            LocalTime horaViaje1 = LocalTime.of(10, 30);
            
            // Constructor Parada: Parada(String direccion, double precio)
            List<Parada> paradasViaje1 = new ArrayList<>();
            paradasViaje1.add(new Parada("Obregon", 250.0));
            paradasViaje1.add(new Parada("Tutuli", 50.0));
            paradasViaje1.add(new Parada("ITSON", 30.0));
            paradasViaje1.add(new Parada("Navojoa", 0.0)); 

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
            viajeDAO.save(viaje1); 
            LOGGER.log(Level.INFO, "Viaje de prueba 1 insertado con ID: {0}", viaje1.getId());
            
            // viaje 2: Obregon a Esperanza ---
            LocalDate fechaViaje2 = LocalDate.now().plusDays(2);
            LocalTime horaViaje2 = LocalTime.of(14, 0);
            
            List<Parada> paradasViaje2 = new ArrayList<>();
            paradasViaje2.add(new Parada("Obregon", 70.0));
            paradasViaje2.add(new Parada("Central Camiones", 40.0));
            paradasViaje2.add(new Parada("Esperanza", 0.0));

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
            viajeDAO.save(viaje2);
            LOGGER.log(Level.INFO, "Viaje de prueba 2 insertado con ID: {0}", viaje2.getId());

            LOGGER.info("Insercion de datos de prueba finalizada con exito.");

        } catch (DatabaseException | ValidationException e) {
            LOGGER.log(Level.SEVERE, "Error al insertar datos de prueba. Asegurate de que MongoDB este corriendo y la conexion sea correcta: " + e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
        new InicializadorDatosPrueba().agregarDatosPrueba();
    }
}
