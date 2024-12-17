package Modelo.Dominio.Accesos_a_heladeras;

import Modelo.Dominio.colaborador.Colaborador;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "SolicitudTarjeta")
public class SolicitudTarjeta {
    @Id
    @GeneratedValue
    private Integer id_solicitud_tarjeta;
    @ManyToOne
    @JoinColumn(name = "destinatario", referencedColumnName = "id_colaborador")
    private Colaborador destinatario;
    @Column(name = "cantidad_de_tarjetas")
    private int cantidadDeTarjetas;

    //Constructores--------------------------------------------------------------------------------------------------
    public SolicitudTarjeta(Colaborador destinatario, int cantidadDeTarjetas) {
        this.destinatario = destinatario;
        this.cantidadDeTarjetas = cantidadDeTarjetas;
    }

    public SolicitudTarjeta() {

    }
}
