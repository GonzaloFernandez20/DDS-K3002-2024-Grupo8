package Modelo.Dominio.suscripcion;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.heladera.Heladera;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Suscripcion")
public class Suscripcion {
    @Id
    @GeneratedValue
    private Integer id_suscripcion;
    @Column(name = "evento")
    private String evento;
    @ManyToOne
    @JoinColumn(name = "id_colaborador")
    private Colaborador suscripto;
    @ManyToOne
    @JoinColumn(name = "id_heladera")
    private Heladera heladera;

    //Constructores -----------------------------------------------
    public Suscripcion(String evento, Colaborador suscripto, Heladera heladera) {
        this.evento = evento;
        this.suscripto = suscripto;
        this.heladera = heladera;
    }

    public Suscripcion() {

    }
    //Metodos -------------------------------------------------------
    public boolean estaSuscritoAl(String evento){
        return this.evento.equals(evento);
    }

    public boolean correspondaConLaReferencia(String eventoReferencia,  Integer colaborador){
        return this.suscripto.getId_colaborador().equals(colaborador) && this.evento.startsWith(eventoReferencia.split("\\s+")[0]);
    }
}
