package Modelo.Brokers;
import Repositories.heladera.SensoreoDeTemperaturaRepository;
import Modelo.Dominio.heladera.SensoreoDeTemperatura;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Component
public class BrokerTemperaturaListener {

    private final ServicioBroker servicioBroker;
    private final SensoreoDeTemperaturaRepository sensoreoDeTemperaturaRepository;

    @Autowired
    public BrokerTemperaturaListener(SensoreoDeTemperaturaRepository sensoreoDeTemperaturaRepository,
                                     ServicioBroker servicioBroker) throws Exception {
        this.sensoreoDeTemperaturaRepository = sensoreoDeTemperaturaRepository;
        this.servicioBroker = servicioBroker;
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

            // Declarar la cola en caso de que no exista
            canal.queueDeclare("temperaturas", true, false, false, null);
            System.out.println("Escuchando mensajes en la cola 'temperaturas'...");

            // Crear el callback para manejar los mensajes recibidos
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                // Extraemos el mensaje recibido
                String mensaje = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println("Mensaje recibido: " + mensaje);

                // Procesamos el mensaje
                procesarMensaje(mensaje);
            };

            // Comenzamos a consumir mensajes de la cola "temperaturas"
            canal.basicConsume("temperaturas", true, deliverCallback, consumerTag -> {});
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al escuchar mensajes: " + e.getMessage());
        }
    }

    private void procesarMensaje(String mensaje) {
        try {
            // El mensaje tiene el formato "1: 5.6°C"
            String[] partes = mensaje.split(": ");
            String identificador = partes[0]; // Ejemplo: "1"
            String temperaturaStr = partes[1].replace("°C", "").trim(); // Extraemos el valor numérico

            // Convertimos la temperatura a un número
            double temperatura = Double.parseDouble(temperaturaStr);

            System.out.println("Identificador: " + identificador);
            System.out.println("Temperatura: " + temperatura + "°C");

            // Buscamos el sensor en la BD
            Optional<SensoreoDeTemperatura> sensoreoTemperatura = sensoreoDeTemperaturaRepository.obtenerSensorDeHeladera(identificador);

            if (sensoreoTemperatura.isPresent()) {
                sensoreoTemperatura.get().actualizarTemperatura((float) temperatura);
            } else {
                System.err.println("No se encontró el sensor con el ID: " + identificador);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error procesando el mensaje: " + mensaje);
        }
    }
}
