package Modelo.Brokers;

import Repositories.heladera.SensoreoDeMovimientoRepository;
import Modelo.Dominio.heladera.SensoreoDeMovimiento;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Component
public class BrokerMovimientoListener {

    private final ServicioBroker servicioBroker;
    private final SensoreoDeMovimientoRepository sensoreoDeMovimientoRepository;

    @Autowired
    public BrokerMovimientoListener(ServicioBroker servicioBroker, SensoreoDeMovimientoRepository sensoreoDeMovimientoRepository) throws Exception {
        this.servicioBroker = servicioBroker;
        this.sensoreoDeMovimientoRepository = sensoreoDeMovimientoRepository;
        this.servicioBroker.conectar();  // Conectamos al broker
        this.servicioBroker.crearNuevaCola("temperaturas");  // Aseguramos que la cola exista
    }

    @PostConstruct
    public void iniciarEscucha() {
        new Thread(this::escucharMensajes).start();
    }

    private void escucharMensajes() {

        try {
            // Obtener el canal y conectar al broker
            Channel canal = servicioBroker.getCanal();
            //TODO loggear
            System.out.println("Escuchando mensajes en la cola 'movimientos'...");

            // Crear el callback para manejar los mensajes recibidos
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                // Extraemos el mensaje recibido
                String mensaje = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println("Mensaje recibido: " + mensaje);

                // Procesamos el mensaje
                procesarMensaje(mensaje);
            };

            // Comenzamos a consumir mensajes de la cola "temperaturas"
            canal.basicConsume("movimientos", true, deliverCallback, consumerTag -> {});
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al escuchar mensajes: " + e.getMessage());
        }
    }

    private void procesarMensaje(String id_sensor) {
        //TODO loogear
        System.out.println("Alerta de intento de robo recibida: Sensor ID = " + id_sensor);

        Optional<SensoreoDeMovimiento> sensoreoAvisoRobo = sensoreoDeMovimientoRepository.obtenerSensorDeHeladera(id_sensor);

        if (sensoreoAvisoRobo.isPresent()) {
            sensoreoAvisoRobo.get().enviarAlerta();
        } else {
            System.err.println("No se encontró el sensor con el ID: " + id_sensor);
        }
    }
}
