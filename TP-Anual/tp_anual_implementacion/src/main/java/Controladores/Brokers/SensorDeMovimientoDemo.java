package Controladores.Brokers;

import Modelo.Brokers.ServicioBroker;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SensorDeMovimientoDemo {
//
//    private final ServicioBroker servicioBroker;
//
//    @Autowired
//    public SensorDeMovimientoDemo(ServicioBroker servicioBroker) throws Exception {
//        this.servicioBroker = servicioBroker;
//        this.servicioBroker.conectar();
//        this.servicioBroker.crearNuevaCola("movimientos");
//    }
//
//    @PostMapping("/DetectarMovimiento")
//    public ResponseEntity<String> detectarMovimiento(@RequestParam String id_sensor) throws Exception {
//        servicioBroker.enviarMensaje("movimientos", id_sensor);
//        return ResponseEntity.ok().body("Alerta de intento de robo reportada con exito");
//    }
//
//    @PreDestroy
//    public void cerrarConexion() {
//        try {
//            if (servicioBroker != null) {
//                servicioBroker.cerrarConexion();
//                System.out.println("Conexión al broker cerrada.");
//            }
//        } catch (Exception e) {
//            System.err.println("Error al cerrar la conexión al broker: " + e.getMessage());
//        }
//    }
}

