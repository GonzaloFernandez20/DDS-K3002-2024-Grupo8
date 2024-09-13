package Modelo.Dominio.sensoreos;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

// es un componente físico, lo anoto como una clase para simular que el sensorFisico se comunica con el BrokerTemperatura.
public class SensorFisico {
    private int id;

    public SensorFisico(int id) {
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
                    // Genera una temperatura aleatoria para enviar a la heladera
                    float temperatura = generarTemperaturaAleatoria();
                    String mensaje = id + "," + temperatura;
                    out.println(mensaje);
                    System.out.println("Datos enviados: Sensor ID = "+id +", Temperatura = "+temperatura);
                }
            };
            // Inicia el temporizador para que ejecute la tarea cada 5 minutos (300000 ms)
            timer.scheduleAtFixedRate(task, 0, 300000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private float generarTemperaturaAleatoria() {
        Random random = new Random();
        return 2.0f + (8.0f - 2.0f) * random.nextFloat(); // Genera una temperatura entre 2 y 8 grados
    }
}
