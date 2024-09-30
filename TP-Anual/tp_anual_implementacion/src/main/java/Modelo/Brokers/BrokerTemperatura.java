package Modelo.Brokers;
import Modelo.Dominio.sensoreos.SensoreoTemperatura;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;

public class BrokerTemperatura {
        private int puerto;
        private HashMap<Integer, SensoreoTemperatura> diccionarioSensores;

        public BrokerTemperatura(int puerto) {
            this.puerto = puerto;
            diccionarioSensores = new HashMap<>();
        }

        public void sumarSensoreo(SensoreoTemperatura sensoreoTemperatura) {
            diccionarioSensores.put(sensoreoTemperatura.getId(), sensoreoTemperatura);
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
                double temperatura = Double.parseDouble(partes[1]);
                System.out.println("Datos recibidos: Sensor ID = " + id + ", Temperatura = " + temperatura);

                SensoreoTemperatura sensoreoTemperatura = diccionarioSensores.get(id);
                sensoreoTemperatura.actualizar(temperatura);

            }
        }
}
