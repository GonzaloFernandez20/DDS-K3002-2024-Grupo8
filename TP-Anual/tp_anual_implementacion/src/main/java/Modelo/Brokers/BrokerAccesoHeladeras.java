package Modelo.Brokers;

import Modelo.Dominio.Accesos_a_heladeras.GestorDeAccesosAHeladeras;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.sensoreos.SensoreoTemperatura;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class BrokerAccesoHeladeras {
    private int puerto;
    private HashMap<Integer, Heladera> diccionarioHeladeras;

    public BrokerAccesoHeladeras(int puerto) {
        this.puerto = puerto;
        diccionarioHeladeras = new HashMap<>();
    }

    public void iniciarServidor() {
        try (ServerSocket serverSocket = new ServerSocket(puerto)) {
            System.out.println("Servidor iniciado en el puerto " + puerto);

            while (true) {
                Socket clienteSocket = serverSocket.accept();
                new Thread(new BrokerAccesoHeladeras.ClienteHandler(clienteSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
            int idHeladera = Integer.parseInt(partes[0]);
            String codigoTarjeta = partes[1];
            System.out.println("Datos recibidos: Heladera ID = " + idHeladera + ", Codigo tarjeta = " + codigoTarjeta);

            Heladera heladera = diccionarioHeladeras.get(idHeladera);
            GestorDeAccesosAHeladeras.getInstancia().autorizarApertura(codigoTarjeta, heladera);
        }
    }
}
