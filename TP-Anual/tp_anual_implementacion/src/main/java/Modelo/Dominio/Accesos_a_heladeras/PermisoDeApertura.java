package Modelo.Dominio.Accesos_a_heladeras;

import Modelo.Dominio.contribucion.ContribucionConApertura;
import Modelo.Dominio.heladera.Heladera;
import jakarta.persistence.*;

import java.time.LocalDateTime;
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "PermisoDeApertura")
public abstract class PermisoDeApertura {
    @Id
    @GeneratedValue
    private Integer id_permiso_de_apertura;
    @ManyToOne
    @JoinColumn(name = "id_heladera", referencedColumnName = "id_heladera")
    private Heladera heladera;
    @Enumerated(EnumType.STRING)
    private MotivoApertura motivo;
    @Column(name = "esta_vencida")
    private boolean estaVencida;
    @Column(name = "hora_en_que_vence")
    public LocalDateTime horaEnQueVence;

    public PermisoDeApertura(Heladera heladera, MotivoApertura motivo) {
        this.heladera = heladera;
        this.motivo = motivo;
        this.estaVencida = false;
        this.horaEnQueVence = LocalDateTime.now().plusHours(3);
    }
    public abstract boolean esValida(Heladera heladera);
    // ----------> Getters y Setters
    public Heladera getHeladera() {
        return heladera;
    }
    public MotivoApertura getMotivo() {
        return motivo;
    }

    public void setHeladera(Heladera heladera) {
        this.heladera = heladera;
    }

    public void setMotivo(MotivoApertura motivo) {
        this.motivo = motivo;
    }

    public boolean isEstaVencida() {
        return estaVencida;
    }

    public void setEstaVencida(boolean estaVencida) {
        this.estaVencida = estaVencida;
    }

    public LocalDateTime getHoraEnQueVence() {
        return horaEnQueVence;
    }

    public void setHoraEnQueVence(LocalDateTime horaEnQueVence) {
        this.horaEnQueVence = horaEnQueVence;
    }

}
