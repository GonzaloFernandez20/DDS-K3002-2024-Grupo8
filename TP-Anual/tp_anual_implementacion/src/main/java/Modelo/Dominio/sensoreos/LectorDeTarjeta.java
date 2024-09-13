package Modelo.Dominio.sensoreos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
                    // Genera el codigo permitido. luego probar con el denegado
                    String codigo = generarCodigoPermitido();
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

    private String generarCodigoPermitido() {
        return "12345678";
    }

    private String generarCodigoDenegado() {
        return "87654321";
    }
}
