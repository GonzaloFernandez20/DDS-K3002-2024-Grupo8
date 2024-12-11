package Controladores.Brokers;

import Modelo.Brokers.ServicioBroker;
import Modelo.Dominio.Accesos_a_heladeras.GestorDeAperturasAHeladeras;
import Modelo.Dominio.Repositories.heladera.HeladeraRepository;
import Modelo.Dominio.heladera.Heladera;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class LectorDeTarjetasDemo {

    private final ServicioBroker servicioBroker;
    private final GestorDeAperturasAHeladeras gestorDeAperturasAHeladeras;
    private final HeladeraRepository heladeraRepository;

    @Autowired
    public LectorDeTarjetasDemo(ServicioBroker servicioBroker, GestorDeAperturasAHeladeras gestorDeAperturasAHeladeras, HeladeraRepository heladeraRepository) throws Exception {
        this.servicioBroker = servicioBroker;
        this.gestorDeAperturasAHeladeras = gestorDeAperturasAHeladeras;
        this.heladeraRepository = heladeraRepository;
        this.servicioBroker.conectar();
        this.servicioBroker.crearNuevaCola("aperturas");
    }

    @PostMapping("/RegistrarApertura/Viandas")
    public ResponseEntity<String> registrarAperturaParaDonacion(@RequestParam String codigoDeTarjeta,
                                                    @RequestParam String id_heladera){
        boolean aperturaAutorizada = false;

        Optional<Heladera> heladera = heladeraRepository.obtenerHeladeraSegunID(id_heladera);
        if (heladera.isPresent()){
            aperturaAutorizada = gestorDeAperturasAHeladeras.autorizarApertura(codigoDeTarjeta, heladera.get());
        }

        if (aperturaAutorizada){
            return ResponseEntity.ok().body("Apertura autorizada con exito!");
        }else {
            return ResponseEntity.badRequest().body("No se pudo autorizar la apertura");
        }
    }

//    @PostMapping("/RegistrarApertura/DistribucionDeViandas")
//    public ResponseEntity<String> registrarAperturaParaDistribucion(@RequestParam String codigoDeTarjeta,
//                                                    @RequestParam String id_heladera){
//        boolean aperturaAutorizada = false;
//
//        Optional<Heladera> heladera = heladeraRepository.obtenerHeladeraSegunID(id_heladera);
//        if (heladera.isPresent()){
//            aperturaAutorizada = gestorDeAperturasAHeladeras.autorizarApertura(codigoDeTarjeta, heladera.get());
//        }
//
//        if (aperturaAutorizada){
//            return ResponseEntity.ok().body("Apertura autorizada con exito!");
//        }else {
//            return ResponseEntity.badRequest().body("No se pudo autorizar la apertura");
//        }
//    }

    @PreDestroy
    public void cerrarConexion() {
        try {
            if (servicioBroker != null) {
                servicioBroker.cerrarConexion();
                System.out.println("Conexión al broker cerrada.");
            }
        } catch (Exception e) {
            System.err.println("Error al cerrar la conexión al broker: " + e.getMessage());
        }
    }
}
