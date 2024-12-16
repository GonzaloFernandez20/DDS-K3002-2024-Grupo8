package Controladores.Brokers;

import Modelo.Brokers.ServicioBroker;
import com.rabbitmq.client.GetResponse;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
public class LectorDeTarjetasDemo {

    private final ServicioBroker servicioBroker;

    @Autowired
    public LectorDeTarjetasDemo(ServicioBroker servicioBroker) throws Exception {
        this.servicioBroker = servicioBroker;
        this.servicioBroker.conectar();
        this.servicioBroker.crearNuevaCola("aperturas");
    }

    @PostMapping("/AutorizarApertura")
    public ResponseEntity<String> solicitarAutorizacionApertura(@RequestParam String codigoDeTarjeta,
                                                                @RequestParam String id_heladera) throws IOException {
        try {
            String mensaje = codigoDeTarjeta + ": " + id_heladera;
            servicioBroker.enviarMensaje("autorizacion_aperturas", mensaje);
        } catch (Exception e) {
            //TODO loggear
            System.err.println("Error al solicitar la autorización de apertura: " + e.getMessage());
        }

        Channel canal = servicioBroker.getCanal();
        long startTime = System.currentTimeMillis();
        long timeout = 10000; // 10 segundos de espera máximo

        while ((System.currentTimeMillis() - startTime) < timeout) {
            GetResponse response = canal.basicGet("cola_respuestas", true); // Obtener mensaje de la cola
            if (response != null) {
                String respuesta = new String(response.getBody(), StandardCharsets.UTF_8);
                if (respuesta.equals("true")){
                    //TODO loggear
                    return ResponseEntity.ok().body("Apertura autorizada");
                }else {
                    //TODO loggear
                    return ResponseEntity.badRequest().body("Apertura denegada");
                }
            }
        }
        // Si no se recibe una respuesta en el tiempo límite
        //TODO loggear: se acabo el tiempo de espera de la respuesta del broker
        return ResponseEntity.badRequest().body("No se recibió respuesta del broker a tiempo");
    }

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
