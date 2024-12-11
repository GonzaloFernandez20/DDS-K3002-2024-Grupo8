package Modelo.Dominio.Accesos_a_heladeras;

import Modelo.Dominio.contribucion.ContribucionConApertura;
import Modelo.Dominio.heladera.Heladera;
import jakarta.persistence.*;

import java.time.LocalDateTime;
@Entity
@Table(name = "apertura_con_permiso")
public class AperturaConPermiso extends Apertura {
    @ManyToOne // una colaboracion para distribuir viandas tiene asociadas dos aperturas (una para sacar y otra para ingresar)
    @JoinColumn(name = "contribucion", referencedColumnName = "id_contribucion")
    private ContribucionConApertura contribucionAsociada;
    @Column(name = "hora_vencimiento")
    private LocalDateTime horaEnQueVence;
    @Column(name = "fue_concretada")
    private boolean fueConcretadaLaApertura;

    //Constructores ---------------------------------------------------------------------------------------------
    public AperturaConPermiso(Heladera heladera, MotivoApertura motivo, ContribucionConApertura contribucion) {
        this.heladera = heladera;
        this.motivo = motivo;
        this.contribucionAsociada = contribucion;
        this.horaEnQueVence = LocalDateTime.now().plusHours(3);
        this.fueConcretadaLaApertura = false;
    }

    public AperturaConPermiso() {
    }

    //Metodos ----------------------------------------------------------------------------------------------------
    public boolean esValidaEn(Heladera heladera_consultada){
        return !fueConcretadaLaApertura && !vencioElPermiso() && heladera.equals(heladera_consultada);
    }

    public boolean vencioElPermiso() {
        return horaEnQueVence.isBefore(LocalDateTime.now());
    }

    //TODO: revisar cuando se usa esto y si no fue hecho temporalmente cuanso no habia bd
    public boolean aperturaParaEntregaDeDonacionEntre(LocalDateTime fechaInicio, LocalDateTime fechaFin){
        return (horaEnQueVence.isAfter(fechaInicio) && horaEnQueVence.isBefore(fechaFin))
                || horaEnQueVence.equals(fechaInicio) || horaEnQueVence.isEqual(fechaFin);
    }


    //Getters y Setters ------------------------------------------------------------------------------------------
    public ContribucionConApertura getContribucion() {return contribucionAsociada;}
    public void setContribucion(ContribucionConApertura contribucion) {this.contribucionAsociada = contribucion;}

    public LocalDateTime getHoraEnQueVence() {return horaEnQueVence;}
    public void setHoraEnQueVence(LocalDateTime horaEnQueVence) {this.horaEnQueVence = horaEnQueVence;}

    public boolean getFueConcretadaLaApertura() {return fueConcretadaLaApertura;}
    public void setFueConcretadaLaApertura(boolean fueConcretadaLaApertura) {this.fueConcretadaLaApertura = fueConcretadaLaApertura;}


}
