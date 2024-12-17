package Modelo.Dominio.Accesos_a_heladeras;

import Modelo.Dominio.heladera.Heladera;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Apertura")
public class Apertura {
    @Id
    @GeneratedValue
    private Integer id_Apertura;
    @ManyToOne
    @JoinColumn(name = "heladera", referencedColumnName = "id_heladera")
    protected Heladera heladera;
    @Enumerated(EnumType.STRING)
    @Column(name = "motivo_de_apertura")
    protected MotivoApertura motivo;
    @Column(name = "fecha_apertura")
    public LocalDateTime fechaApertura;
    @Column(name = "cantidad_viandas")
    protected int cantidadViandasInvolucradas;

    //Constructores ------------------------------------------------------------------

    public Apertura(Heladera heladera, MotivoApertura motivo){
        this.heladera = heladera;
        this.motivo = motivo;
    }

    public Apertura() {

    }
}
