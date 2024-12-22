package Modelo.Dominio.Accesos_a_heladeras;

import Modelo.Dominio.contribucion.ContribucionConApertura;
import Modelo.Dominio.heladera.Heladera;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "apertura_con_permiso")
public class AperturaConPermiso extends Apertura {
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE }) // una colaboracion para distribuir viandas tiene asociadas dos aperturas (una para sacar y otra para ingresar)
    @JoinColumn(name = "contribucion", referencedColumnName = "id_contribucion")
    private ContribucionConApertura contribucionAsociada;
    @Column(name = "hora_vencimiento")
    private LocalDateTime horaEnQueVence;
    @Column(name = "fue_concretada")
    private boolean fueConcretadaLaApertura;

    //Constructores ---------------------------------------------------------------------------------------------
    public AperturaConPermiso(Heladera heladera, MotivoApertura motivo, ContribucionConApertura contribucion) {
        super(heladera,motivo);
        this.contribucionAsociada = contribucion;
        this.horaEnQueVence = LocalDateTime.now().plusHours(3);
        this.fueConcretadaLaApertura = false;
        this.cantidadViandasInvolucradas = 0;
    }

    public AperturaConPermiso() {
    }

    //Metodos ----------------------------------------------------------------------------------------------------
    public boolean esValidaEn(Heladera heladera_consultada){
        return !fueConcretadaLaApertura && !vencioElPermiso() && heladera.getid_heladera().equals(heladera_consultada.getid_heladera());
    }

    public boolean vencioElPermiso() {
        return horaEnQueVence.isBefore(LocalDateTime.now());
    }
}
