package Modelo.Dominio.Accesos_a_heladeras;

import Modelo.Dominio.persona.PersonaHumana;
import jakarta.persistence.*;

@Entity
@Table(name = "SolicitudTarjeta")
public class SolicitudTarjeta {
    @Id
    @GeneratedValue
    private Integer id_solicitud_tarjeta;
    @ManyToOne
    @JoinColumn(name = "id_persona_humana")
    private PersonaHumana destinatario;
    @Column(name = "cantidad_de_tarjetas")
    private int cantidadDeTarjetas;

    public SolicitudTarjeta(PersonaHumana destinatario, int cantidadDeTarjetas) {
        this.destinatario = destinatario;
        this.cantidadDeTarjetas = cantidadDeTarjetas;
    }

    public PersonaHumana getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(PersonaHumana destinatario) {
        this.destinatario = destinatario;
    }

    public int getCantidadDeTarjetas() {
        return cantidadDeTarjetas;
    }

    public void setCantidadDeTarjetas(int cantidadDeTarjetas) {
        this.cantidadDeTarjetas = cantidadDeTarjetas;
    }
}
