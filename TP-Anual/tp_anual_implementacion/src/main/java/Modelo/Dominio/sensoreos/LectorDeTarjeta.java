package Modelo.Dominio.sensoreos;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.SecureRandom;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class LectorDeTarjeta {
    private int idHeladera;

    public LectorDeTarjeta(int id) {
        this.idHeladera = id;
    }

    public void enviarDatos(String host, int puerto) {
        try (Socket socket = new Socket(host, puerto);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            // Configura un Timer para enviar datos cada 5 minutos
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    // Genera una temperatura aleatoria para enviar a la heladera
                    String codigo = generarCodigo();
                    String mensaje = idHeladera + "," + codigo;
                    out.println(mensaje);
                    System.out.println("Datos enviados: Sensor ID = "+idHeladera +", Codigo = "+codigo);
                }
            };
            // Inicia el temporizador para que ejecute la tarea cada 5 minutos (300000 ms)
            timer.scheduleAtFixedRate(task, 0, 300000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String generarCodigo() {
        // Crear una instancia de SecureRandom para generar números aleatorios
        SecureRandom random = new SecureRandom();

        // Generar un número aleatorio de 8 dígitos
        int numeroAleatorio = 10000000 + random.nextInt(90000000);

        // Convertir el número aleatorio a una cadena de 8 dígitos
        return String.format("%08d", numeroAleatorio);
    }
}
