package TPAnual;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;

public class Validador {
    
    static File listaDeConstrasenias = new File("TP-IntegradorAnual/10KPasswords.txt");
    
    public static Boolean validarRegistro(String contrasenia){
        return cumpleCantCaracteres(contrasenia) && !encontrarPalabra(contrasenia);  
    }
 
    private static Boolean encontrarPalabra(String palabra){
        Boolean encontro = false;
        BufferedReader bufferDeLectura;

        try {
            
            if (listaDeConstrasenias.exists()) {
                bufferDeLectura = new BufferedReader(new FileReader(listaDeConstrasenias));
            
                String lineaLeida;

                while((lineaLeida = bufferDeLectura.readLine()) != null) {
                    
                    
                    String[] palabras = lineaLeida.split(" "); //Separa todas las palabras en el espacio

                    for(int i = 0 ; i < palabras.length ; i++) {

                        if(palabras[i].equals(palabra)) {
                            encontro = true;
                        }
                    }
                }
            }else {
                throw new IllegalAccessError("No existe el archivo.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return encontro;
    }

    private static Boolean cumpleCantCaracteres(String contrasenia){
        return contrasenia.length() > 8 && contrasenia.length() < 64;
    }
}