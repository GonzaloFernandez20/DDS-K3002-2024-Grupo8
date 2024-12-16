package Modelo.Dominio.suscripcion;

import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.sistema.RegistroDeHeladeras;

import java.util.List;

public class CreadorDeMensajes { // Como es una clase solo de comportamiento, no se instancia y sus metodos son estaticos.

    public static String crearMensaje(String evento, Heladera heladera){
        String mensaje = String.format( evento + " en la heladera: %s (%s).", heladera.getUbicacion().getNombreDelPunto(),
                                                                           heladera.getUbicacion().getDireccion() );

        if (evento.equalsIgnoreCase("Se produjo una falla\n")){
            String heladerasSugeridas = sugerirHeladeras(heladera, heladera.cantViandasEnStock());
            mensaje += heladerasSugeridas;
        }
        return mensaje;
    }
    // Ejemplo: "Se produjo una falla en la heladera Medrano (Medrano 981)." // -> "Se produjo una falla" es el evento.
    // Ejemplo: "Quedan 5 viandas en la heladera Medrano (Medrano 981)." // -> "Quedan 5 viandas" es el evento.

    public static String sugerirHeladeras(Heladera heladera, int cantidadParaRedistribuir){
        List <Heladera> heladeras = RegistroDeHeladeras.getInstancia().sugerirHeladerasDestino(heladera, cantidadParaRedistribuir);
        if(!heladeras.isEmpty()){
        StringBuilder heladerasSugeridas = new StringBuilder(" Heladeras sugeridas para redistribuir las viandas: \n");
            for (Heladera sugerencia : heladeras){
                heladerasSugeridas.append(String.format("\tHeladera: %s (%s) \n", sugerencia.getUbicacion().getNombreDelPunto(),
                        sugerencia.getUbicacion().getStringDireccion()));
            }
        return heladerasSugeridas.toString();
        }
        else{
            return " No hay heladeras disponibles para redistribucion.";
        }
    }
    /* Ejemplo: "Se produjo una falla en la heladera Medrano (Medrano 981). Heladeras sugeridas para redistribuir las viandas: "
                   Heladera: nombre (direccion)
                   Heladera: nombre (direccion)
                   Heladera: nombre (direccion)
                   etc.
    */
}