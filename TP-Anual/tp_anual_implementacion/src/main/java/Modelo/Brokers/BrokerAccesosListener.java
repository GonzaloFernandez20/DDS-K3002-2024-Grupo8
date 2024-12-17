package Modelo.Brokers;


import Modelo.Dominio.Accesos_a_heladeras.GestorDeAperturasAHeladeras;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class BrokerAccesosListener {
    private final ServicioBroker servicioBroker;
    private final GestorDeAperturasAHeladeras gestorDeAperturasAHeladeras;

    @Autowired
    public BrokerAccesosListener(ServicioBroker servicioBroker, GestorDeAperturasAHeladeras gestorDeAperturasAHeladeras) throws Exception {
        this.servicioBroker = servicioBroker;
        this.gestorDeAperturasAHeladeras = gestorDeAperturasAHeladeras;
        this.servicioBroker.conectar();  // Conectamos al broker
        this.servicioBroker.crearNuevaCola("autorizacion_aperturas");  // Aseguramos que la cola exista
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
            System.out.println("Escuchando mensajes en la cola 'autorizacion_aperturas'...");

            // Crear el callback para manejar los mensajes recibidos
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                // Extraemos el mensaje recibido
                String mensaje = new String(delivery.getBody(), StandardCharsets.UTF_8);
                //TODO loggear
                System.out.println("Mensaje recibido: " + mensaje);

                // Procesamos el mensaje
                procesarMensaje(mensaje);
            };
            // Comenzamos a consumir mensajes de la cola "autorizacion_aperturas"
            canal.basicConsume("autorizacion_aperturas", true, deliverCallback, consumerTag -> {});

        } catch (Exception e) {
            e.printStackTrace();
            //TODO loggear
            System.err.println("Error al escuchar mensajes: " + e.getMessage());
        }
    }

    private void procesarMensaje(String mensaje) {
        Channel canal = null;
        try {
            String[] partes = mensaje.split(": ");
            String tarjeta = partes[0];
            int idHeladera = Integer.parseInt(partes[1]);
            //TODO loggear
            System.out.println("En la heladera { " + idHeladera + " } se solicitó una autorización de apertura con la tarjeta " + tarjeta);

            boolean estaAutorizadaLaApertura = gestorDeAperturasAHeladeras.autorizarApertura(tarjeta, idHeladera);

            String respuesta = String.valueOf(estaAutorizadaLaApertura);
            canal = servicioBroker.getCanal();
            canal.queueDeclare("cola_respuestas", true, false, false, null);
            canal.basicPublish("", "cola_respuestas", null, respuesta.getBytes(StandardCharsets.UTF_8));

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error procesando el mensaje: " + mensaje);
        }
    }
}
