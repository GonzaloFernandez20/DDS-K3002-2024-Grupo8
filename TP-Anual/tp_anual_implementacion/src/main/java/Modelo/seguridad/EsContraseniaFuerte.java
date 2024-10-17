package Modelo.seguridad;

import Modelo.Excepciones.ExcepcionContraseniaDebil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class EsContraseniaFuerte extends Criterio{
    static File listaDeContrasenias = new File(EsContraseniaFuerte.class.getClassLoader().getResource("utils/10KPasswords.txt").getFile());
    @Override
    public boolean criterioSeguridad(String contrasenia) throws Exception {
        boolean esFuerte = true;

        try(BufferedReader bufferDeLectura = new BufferedReader(new FileReader(listaDeContrasenias))) {
                // Bloque "try-with-resources" para asegurar que el buffer se cierre automaticamente despues de su uso.
            if (listaDeContrasenias.exists()) {

                String lineaLeida;

                while((lineaLeida = bufferDeLectura.readLine()) != null) {

                    String[] palabras = lineaLeida.split(" "); // -> Separa todas las palabras en el espacio
                    for (String palabra : palabras) {
                        if (palabra.equals(contrasenia)) {
                            esFuerte = false;
                            throw new ExcepcionContraseniaDebil("Contrasenia demasiado debil, elija otra.");
                        }
                    }
                }
            }else {
                throw new IllegalAccessError("No existe el archivo.");
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al cargar la lista de contraseñas comunes.", e);
        }
        return esFuerte;
    }
}
