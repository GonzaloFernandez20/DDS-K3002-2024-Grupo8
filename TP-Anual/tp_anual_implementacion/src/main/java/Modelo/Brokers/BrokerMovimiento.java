package Modelo.Brokers;

import Modelo.Dominio.heladera.SensoreoDeMovimiento;
import Modelo.Dominio.heladera.SensoreoDeTemperatura;
import Modelo.Dominio.heladera.SensoreoDeMovimiento;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class BrokerMovimiento {
    private int puerto;
    private HashMap<Integer, SensoreoDeMovimiento> diccionarioSensores;

    public BrokerMovimiento(int puerto) {
        this.puerto = puerto;
        diccionarioSensores = new HashMap<>();
    }

    public void sumarSensoreo(SensoreoDeMovimiento sensoreoAvisoRobo) {
        diccionarioSensores.put(sensoreoAvisoRobo.getId(), sensoreoAvisoRobo);
    }
    // lo mismo que en broker acceso a heladeras
    private class ClienteHandler implements Runnable {
        private Socket socket;

        public ClienteHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                String mensaje;
                while ((mensaje = in.readLine()) != null) {
                    procesarMensaje(mensaje);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void procesarMensaje(String mensaje) {
            String[] partes = mensaje.split(",");
            int id = Integer.parseInt(partes[0]);
            System.out.println("Datos recibidos: Sensor ID = " + id);

            SensoreoDeMovimiento sensoreoAvisoRobo = diccionarioSensores.get(id);
            sensoreoAvisoRobo.enviarAlerta();
        }
    }
}
