package seguridad;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class EsContraseniaFuerte extends Criterio{
    static File listaDeConstrasenias = new File("src/main/java/seguridad/10KPasswords.txt");

    @Override
    public boolean criterioSeguridad(String contrasenia) {
        boolean esFuerte = true;

        try(BufferedReader bufferDeLectura = new BufferedReader(new FileReader(listaDeConstrasenias))) {
                // Bloque "try-with-resources" para asegurar que el buffer se cierre automaticamente despues de su uso.
            if (listaDeConstrasenias.exists()) {

                String lineaLeida;

                while((lineaLeida = bufferDeLectura.readLine()) != null) {

                    String[] palabras = lineaLeida.split(" "); // -> Separa todas las palabras en el espacio
                    for (String palabra : palabras) {
                        if (palabra.equals(contrasenia)) {
                            esFuerte = false;
                            break;
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
