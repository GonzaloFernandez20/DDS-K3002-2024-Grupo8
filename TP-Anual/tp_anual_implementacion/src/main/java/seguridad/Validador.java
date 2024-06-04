package seguridad;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Validador {
    
    static File listaDeConstrasenias = new File("seguridad/10KPasswords.txt");
    
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

class Usuario{
    private String userName;
    private String password;

    public void setUserName(String userName){ this.userName = userName; }

    public void setPassword(String password){ this.password = password; }

    public Usuario(String userName, String password){
        if (userName == null || password == null) {
            throw new IllegalArgumentException("Datos incorrectos");
        }
        this.userName = userName;
        this.password = password;
    }

    public String contrasenia(){ return password; }
    public String nombreDeUsuario(){ return userName; }
}