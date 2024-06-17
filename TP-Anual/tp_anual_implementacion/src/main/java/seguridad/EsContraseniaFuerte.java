package seguridad;

import seguridad.Criterio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class EsContraseniaFuerte extends Criterio{
    static File listaDeConstrasenias = new File("src/main/java/seguridad/10KPasswords.txt");

    @Override
    public boolean generarCriterio(String contrasenia) {
        boolean esFuerte = true;
        BufferedReader bufferDeLectura;

        try {

            if (listaDeConstrasenias.exists()) {
                bufferDeLectura = new BufferedReader(new FileReader(listaDeConstrasenias));

                String lineaLeida;

                while((lineaLeida = bufferDeLectura.readLine()) != null) {


                    String[] palabras = lineaLeida.split(" "); //Separa todas las palabras en el espacio

                    for(int i = 0 ; i < palabras.length ; i++) {

                        if (palabras[i].equals(contrasenia)) {
                            esFuerte = false;
                            break;
                        }
                    }
                }
            }else {
                throw new IllegalAccessError("No existe el archivo.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return esFuerte;
    }
}
