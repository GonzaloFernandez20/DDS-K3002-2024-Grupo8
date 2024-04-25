package Correo;

import java.util.List;

public class Cliente {
    String nombre;
    String direccion;
    String codPostal;
    String localidad;
    List<Envio> enviosRecibidos;
    public void recibirEnvio(Envio envio){
        enviosRecibidos.add(envio);
    }

}
