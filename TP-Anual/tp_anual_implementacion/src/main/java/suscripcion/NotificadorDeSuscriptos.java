package suscripcion;

import colaborador.Colaborador;
import heladera.Heladera;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

public class NotificadorDeSuscriptos {
    private Map<String, List<Colaborador>> suscriptos;
    private final Heladera heladera;

    public NotificadorDeSuscriptos(Heladera heladera) {
        this.heladera = heladera;
        this.suscriptos = new HashMap<>();
    }

    public void suscribir(String evento, Colaborador colaborador){
        suscriptos.computeIfAbsent(evento, key -> new ArrayList<>());
        suscriptos.get(evento).add(colaborador);
    }

    public void desuscribir(String evento, Colaborador colaborador){
        suscriptos.get(evento).remove(colaborador);
    }

    public void notificar(String evento){ // A cada colaborador suscripto a ese evento, se le envia un mensaje
        String mensaje = CreadorDeMensajes.crearMensaje(evento, heladera);
        for (Colaborador suscripto : suscriptos.get(evento)){
            suscripto.notificar(mensaje);
        }
    }
}
