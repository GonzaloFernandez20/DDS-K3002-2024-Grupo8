package Modelo.Brokers;

import Modelo.Dominio.Accesos_a_heladeras.GestorDeAccesosAHeladeras;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.sensoreos.SensoreoTemperatura;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class BrokerAccesoHeladeras {
    private int puerto;
    public HashMap<Integer, Heladera> diccionarioHeladeras;
    Socket clienteSocket = null;
    public String respuesta = "Acceso denegado";

    public BrokerAccesoHeladeras(int puerto) {
        this.puerto = puerto;
        diccionarioHeladeras = new HashMap<>();
    }
    // ver desde donde se llama
    public void agregarHeladerasAlDiccionario(Integer idHeladera, Heladera heladera) {
        diccionarioHeladeras.put(idHeladera, heladera);
    }
    public void quitarHeladerasDelDiccionario(Integer idHeladera) {
        diccionarioHeladeras.remove(idHeladera);
    }

    public String getRespuesta() {return respuesta;}

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

        private void procesarMensaje(String mensaje) throws IOException {
            String[] partes = mensaje.split(",");
            int idHeladera = Integer.parseInt(partes[0]);
            String codigoTarjeta = partes[1];
            System.out.println("Datos recibidos: Heladera ID = " + idHeladera + ", Codigo tarjeta = " + codigoTarjeta);

            Heladera heladera = diccionarioHeladeras.get(idHeladera);
            if(GestorDeAccesosAHeladeras.getInstancia().autorizarApertura(codigoTarjeta, heladera)) {
                // Enviar el mensaje "Acceso permitido" al cliente
                PrintWriter salida = new PrintWriter(clienteSocket.getOutputStream(), true);
                respuesta = "Acceso permitido";
            } else {
                // Enviar el mensaje "Acceso denegado" al cliente
                PrintWriter salida = new PrintWriter(clienteSocket.getOutputStream(), true);
                respuesta = "Acceso denegado";
            }
        }
    }


}
