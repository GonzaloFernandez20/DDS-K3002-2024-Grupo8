package Modelo.Dominio.heladera;

import Modelo.Dominio.incidentes.GestorDeIncidentes;
import Modelo.Dominio.incidentes.TipoAlerta;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class SensoreoDeTemperatura {
    private Heladera heladera;
    private Timer timerDeConexion;
    private float ultimaTemperaturaRegistrada;

    //------------------ BROKER -----------------
    private static final int PORT = 12345; // Puerto para la comunicación
    //-------------------------------------------

    public SensoreoDeTemperatura(Heladera heladera) {
        this.heladera = heladera;
        timerDeConexion = new Timer();
    }

    public void actualizarTemperatura(float temperatura) {
        timerDeConexion.cancel();
        this.ultimaTemperaturaRegistrada = temperatura;
        if (!heladera.getModelo().controlarTemperatura(temperatura)){
            GestorDeIncidentes.reportarAlerta(heladera, TipoAlerta.TEMPERATURA);
        }else{
            iniciarTimer();
        }
    }

    // Método que programa una tarea para que se ejecute cada 5 segundos
    public void iniciarTimer() {
        TimerTask tarea = new TimerTask() {
            @Override
            public void run() {
                GestorDeIncidentes.reportarAlerta(heladera, TipoAlerta.FALLA_DE_CONEXION);
            }
        };

        // Programar la tarea para que se ejecute cada 5 segundos (5000 milisegundos)
        timerDeConexion.scheduleAtFixedRate(tarea, 0, 5000);
    }


    public void iniciar() { // TODO: Check Broker
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Broker SensoreoDeTemperatura iniciado, esperando conexión...");

            // Espera a que un cliente (la heladera) se conecte
            try (Socket clientSocket = serverSocket.accept()) {
                System.out.println("Cliente conectado");

                DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());

                // Configura un Timer para enviar datos cada 5 minutos
                Timer timer = new Timer();
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        // Genera una temperatura aleatoria para enviar a la heladera
                        float temperatura = generarTemperaturaAleatoria();
                        try {
                            out.writeFloat(temperatura);
                            out.flush();
                            heladera.getModelo().controlarTemperatura(temperatura); // Controla el estado de la heladera
                            System.out.println("Temperatura enviada al cliente: " + temperatura);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                };

                // Inicia el temporizador para que ejecute la tarea cada 5 minutos (300000 ms)
                timer.scheduleAtFixedRate(task, 0, 300000);

                // Mantén el servidor en ejecución para seguir enviando temperaturas
                while (true) {
                    // Aquí podrías agregar más lógica si es necesario
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private float generarTemperaturaAleatoria() {
        Random random = new Random();
        return 2.0f + (8.0f - 2.0f) * random.nextFloat(); // Genera una temperatura entre 2 y 8 grados
    }
}

// donde lo vayamos a probar
/*
*public static void main(String[] args) {
        // Inicializa la heladera con un modelo y capacidad
        Heladera heladera = new Heladera(new Modelo(2.0f, 8.0f), 10);
        // Crea una instancia de SensoreoDeTemperatura pasando la heladera
        SensoreoDeTemperatura sensoreoDeTemperatura = new SensoreoDeTemperatura(heladera);
        // Inicia el broker
        sensoreoDeTemperatura.iniciar();
    }
* */
