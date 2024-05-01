package TPAnual;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;

/*
   El sistema debe permitir el registro de usuarios. Por el momento sólo se requiere guardar usuario y contraseña. Esta responsabilidad más adelante será delegada en un componente específico.

   Siguiendo las recomendaciones del OWASP (Proyecto Abierto de Seguridad en Aplicaciones Web) que se ha constituido en un estándar de facto para la seguridad, se pide:

    ● No utilice credenciales por defecto en su software, particularmente en el caso de administradores.
    ● Implemente controles contra contraseñas débiles. Cuando el usuario ingrese una nueva  clave, la misma puede verificarse contra la lista del Top 10.000 de peores contraseñas.
    ● Alinear la política de longitud, complejidad y rotación de contraseñas con las recomendaciones de la Sección 5.1.1.2 para Secretos Memorizados de la Guía NIST2 800-63
 */

public class Validador {
    
    static int lineasTotales;
    static File listaDeConstrasenias = new File("TPAnual/10KPasswords.txt");
    
    public static Boolean validarRegistro(String contrasenia){
        return !buscarPalabra(listaDeConstrasenias, contrasenia);
    }
 
    private static Boolean buscarPalabra(File listaDeContrasenias, String palabra ){
        Boolean encontro = false;
        BufferedReader bufferDeLectura;
    
        try {
            
            if (listaDeContrasenias.exists()) {
                bufferDeLectura = new BufferedReader(new FileReader(listaDeContrasenias));
            
                String lineaLeida;

                while((lineaLeida = bufferDeLectura.readLine()) != null) {
                    
                    lineasTotales = lineasTotales + 1; // Si no quiero imprimir la linea donde lo encontro no me sirve
                    String[] palabras = lineaLeida.split(" "); //Separa todas las palabras en el espacio

                    for(int i = 0 ; i < palabras.length ; i++) {

                        if(palabras[i].equals(palabra)) {

                            encontro = true;
                            //System.out.println("\nEn la linea: " + lineasTotales + " se encontro la palabra a buscar: " + palabra);
                        }
                    }
                }
            }else {
                throw new IllegalAccessError("No existe el archivo.");
            }
            //System.out.println("En total se encotro la palabra: " + palabra + ", " + totalCoincidencias + " Veces en el archivo");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return encontro;
    }
}
