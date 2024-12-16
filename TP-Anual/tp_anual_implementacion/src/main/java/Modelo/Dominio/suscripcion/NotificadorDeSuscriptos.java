package Modelo.Dominio.suscripcion;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.heladera.Heladera;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "NotificadorDeSuscriptos")
public class NotificadorDeSuscriptos {
    @Id
    @GeneratedValue
    private Integer id_notificador_de_suscriptos;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_notificador")
    private List<Suscripcion> suscriptos;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_heladera")
    private Heladera heladera;

    //Constructores ---------------------------------------------------------------------------------------
    public NotificadorDeSuscriptos(Heladera heladera) {
        this.heladera = heladera;
        this.suscriptos = new ArrayList<>();
    }
    public NotificadorDeSuscriptos() {
    }

    //Metodos ---------------------------------------------------------------------------------------------
    public void suscribir(String evento, Colaborador colaborador){
        Optional<Suscripcion> suscripcion = buscarSuscripcion(evento, colaborador);
        if(suscripcion.isPresent()){
            suscripcion.get().setEvento(evento);
        }
        else{
            suscriptos.add(new Suscripcion(evento, colaborador,heladera));
        }
    }

    public void desuscribir(String evento, Colaborador colaborador){
        Optional<Suscripcion> suscripcion = buscarSuscripcion(evento, colaborador);
        if (suscripcion.isPresent()){
            suscriptos.remove(suscripcion.get());
        }
    }

    public Optional<Suscripcion> buscarSuscripcion(String evento, Colaborador colaborador){
        return suscriptos.stream().
                filter(suscripcion -> suscripcion.correspondaConLaReferencia(evento, colaborador.getId_colaborador())).
                findFirst();
    }

    public void notificar(String evento){
        List<Suscripcion> suscriptosANotificar = suscriptos.stream()
                                                           .filter(suscripto ->suscripto.estaSuscritoAl(evento))
                                                           .toList();
        if(!suscriptosANotificar.isEmpty()){
        String mensaje = CreadorDeMensajes.crearMensaje(evento, heladera);
            for (Suscripcion suscripto : suscriptosANotificar){
                suscripto.getSuscripto().notificar(mensaje);
            }
        }
    }
}
