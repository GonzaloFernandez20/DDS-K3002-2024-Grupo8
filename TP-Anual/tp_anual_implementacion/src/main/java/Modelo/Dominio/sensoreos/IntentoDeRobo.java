package Modelo.Dominio.sensoreos;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class IntentoDeRobo {
    private int id;

    public IntentoDeRobo(int id) {
        this.id = id;
    }

    public void enviarDatos(String host, int puerto) {
        try (Socket socket = new Socket(host, puerto);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            // Configura un Timer para enviar datos cada 5 minutos
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    String mensaje = ""+ id;
                    out.println(mensaje);
                    System.out.println("Datos enviados: Sensor ID = "+id);
                }
            };
            // Inicia el temporizador para que ejecute la tarea cada 2.5 minutos (150000 ms)
            timer.scheduleAtFixedRate(task, 0, 150000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
