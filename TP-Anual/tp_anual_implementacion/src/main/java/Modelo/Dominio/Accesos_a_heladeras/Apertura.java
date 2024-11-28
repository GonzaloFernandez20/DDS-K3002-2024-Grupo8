package Modelo.Dominio.Accesos_a_heladeras;

import Modelo.Dominio.contribucion.Vianda;
import Modelo.Dominio.heladera.Heladera;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

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
    @Column(name = "cantiadad_viandas")
    protected int cantidadViandasInvolucradas;

    //Constructores ------------------------------------------------------------------

    /*usar cuando se instancia la apertura de una persona vulnerable*/
    public Apertura(Heladera heladera, MotivoApertura motivo){
        this.heladera = heladera;
        this.motivo = motivo;
        this.fechaApertura = LocalDateTime.now();
        this.cantidadViandasInvolucradas = 1;
    }

    public Apertura() {

    }

    //  Getters y Setters ----------------------------------------------------------------------------------------------
    public Heladera getHeladera() {return heladera;}
    public void setHeladera(Heladera heladera) {this.heladera = heladera;}

    public MotivoApertura getMotivo() {return motivo;}
    public void setMotivo(MotivoApertura motivo) {this.motivo = motivo;}

    public LocalDateTime getFechaApertura() {return fechaApertura;}
    public void setFechaApertura(LocalDateTime fechaApertura) {this.fechaApertura = fechaApertura;}

    public int getCantidadViandasInvolucradas() {return cantidadViandasInvolucradas;}
    public void setCantidadViandasInvolucradas(int cantidadViandasInvolucradas) {this.cantidadViandasInvolucradas = cantidadViandasInvolucradas;}

}
