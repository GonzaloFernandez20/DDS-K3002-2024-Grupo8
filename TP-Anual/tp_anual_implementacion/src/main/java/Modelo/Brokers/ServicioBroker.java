package Modelo.Brokers;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class ServicioBroker {

    private final String host = "localhost";
    private Connection conexion;
    @Getter
    private Channel canal;

    public ServicioBroker() { }

    public void conectar() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        this.conexion = factory.newConnection();
        this.canal = conexion.createChannel();
        System.out.println("Conectado al broker RabbitMQ en " + host);
    }

    public void crearNuevaCola(String nombreDeCola) throws Exception {
        canal.queueDeclare(nombreDeCola, true, false, false, null);
        System.out.println("Cola declarada: " + nombreDeCola);
    }

    public void enviarMensaje(String nombreDeCola, String mensaje) throws Exception {
        canal.basicPublish("", nombreDeCola, null, mensaje.getBytes(StandardCharsets.UTF_8));
        System.out.println("Mensaje enviado a la cola " + nombreDeCola + ": " + mensaje);
    }

    public void cerrarConexion() throws Exception {
        if (canal != null && canal.isOpen()) {
            canal.close();
        }
        if (conexion != null && conexion.isOpen()) {
            conexion.close();
        }
        System.out.println("Conexión cerrada");
    }

}