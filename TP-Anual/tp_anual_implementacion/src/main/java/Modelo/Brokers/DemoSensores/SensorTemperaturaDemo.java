package Modelo.Brokers.DemoSensores;
import Modelo.Brokers.ServicioBroker;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class SensorTemperaturaDemo {

//    private final ServicioBroker servicioBroker;
//
//    @Autowired
//    public SensorTemperaturaDemo(ServicioBroker servicioBroker) throws Exception {
//        this.servicioBroker = servicioBroker;
//        this.servicioBroker.conectar();
//        this.servicioBroker.crearNuevaCola("temperaturas");
//    }
//
//    @Scheduled(fixedRate = 300000) // Cada 5 minutos (300,000 ms)
//    public void enviarTemperaturas() {
//        try {
//            Random random = new Random();
//            int temperatura = random.nextInt(1,10);
//            String mensaje = "1: " + temperatura + "°C";
//
//            servicioBroker.enviarMensaje("temperaturas", mensaje);
//            System.out.println("Temperatura enviada: " + mensaje);
//        } catch (Exception e) {
//            System.err.println("Error al enviar la temperatura: " + e.getMessage());
//        }
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

