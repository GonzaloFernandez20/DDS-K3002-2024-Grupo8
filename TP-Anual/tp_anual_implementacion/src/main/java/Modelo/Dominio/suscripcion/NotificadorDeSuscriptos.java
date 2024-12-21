package Modelo.Dominio.suscripcion;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.heladera.Heladera;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
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
            log.info("Se actualizó la suscripción del colaborador ID:{} del evento: {} al evento: {} de la {} ID:{}",
                    colaborador.getId_colaborador(), suscripcion.get().getEvento(), evento, heladera.getNombreDelPunto(),heladera.getid_heladera());
            suscripcion.get().setEvento(evento);
        }
        else{
            suscriptos.add(new Suscripcion(evento, colaborador,heladera));
            log.info("Se suscribió al colaborador ID:{} al evento: {} de la {} ID: {}",
                    colaborador.getId_colaborador(), evento, heladera.getNombreDelPunto(), heladera.getid_heladera());
        }
    }

    public void desuscribir(String evento, Colaborador colaborador){
        Optional<Suscripcion> suscripcion = buscarSuscripcion(evento, colaborador);
        if (suscripcion.isPresent()){
            suscriptos.remove(suscripcion.get());
            log.info("Se desuscribió al colaborador ID:{} del evento: {} de la {} ID: {}",
                    colaborador.getId_colaborador(), evento, heladera.getNombreDelPunto(), heladera.getid_heladera());
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
            log.info("Se notificó a todos los suscriptos al evento: {} de la {} ID: {}", evento, heladera.getNombreDelPunto(), heladera.getid_heladera());
        }else{
            log.info("No hay ningún colaborador suscripto al evento: {} de la {} ID: {}", evento, heladera.getNombreDelPunto(), heladera.getid_heladera());
        }
    }
}
