package Modelo.Dominio.suscripcion;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.heladera.Heladera;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Entity
@Table(name = "NotificadorDeSuscriptos")
public class NotificadorDeSuscriptos {
    @Id
    @GeneratedValue
    private Integer id_notificador_de_suscriptos;
    @Transient
    private final Map<String, List<Colaborador>> suscriptos;
    @OneToOne(mappedBy = "notificadorDeSuscriptos")
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
        if (suscriptos.get(evento).isEmpty()) suscriptos.remove(evento);
    }

    public void notificar(String evento){ // A cada colaborador suscripto a ese evento, se le envia un mensaje
        String mensaje = CreadorDeMensajes.crearMensaje(evento, heladera);
        if(suscriptos.containsKey(evento)){
            for (Colaborador suscripto : suscriptos.get(evento)){
                suscripto.notificar(mensaje);
            }
        }
    }

    public List<String> eventosALosQueEstaSuscritoUnColaborador(Colaborador colaborador) {
        List<String> eventos = new ArrayList<>();

        suscriptos.forEach( (evento, suscriptores) -> {
            if(suscriptores.contains(colaborador)){
                eventos.add(evento);
            }
        });

        return eventos;
    }

    // ---- Getters y Setters
    public Map<String, List<Colaborador>> getSuscriptos() { return suscriptos; }
    public Heladera getHeladera() { return heladera; }
}
