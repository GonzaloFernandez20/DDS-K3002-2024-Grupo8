package Modelo.Dominio.Accesos_a_heladeras;

import Modelo.Dominio.contribucion.ContribucionConApertura;
import Modelo.Dominio.heladera.Heladera;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Entity
@DiscriminatorValue("ParaColaborar")
public class PermisoDeAperturaParaColaborar extends PermisoDeApertura{
    @OneToOne
    @JoinColumn(name = "id_contribucion", referencedColumnName = "id_contribucion")
    private ContribucionConApertura contribucion;
    @Column(name = "fecha_de_vencimiento")
    private LocalDate fechaDeVencimiento;
    @Column(name = "esta_vencida")
    private boolean estaVencida;

    public PermisoDeAperturaParaColaborar(Heladera heladera, MotivoApertura motivo) {
        super(heladera, motivo);
    }

    public ContribucionConApertura getContribucion() {
        return contribucion;
    }

    public void setContribucion(ContribucionConApertura contribucion) {
        this.contribucion = contribucion;
    }

    public LocalDate getFechaDeVencimiento() {
        return fechaDeVencimiento;
    }

    public void setFechaDeVencimiento(LocalDate fechaDeVencimiento) {
        this.fechaDeVencimiento = fechaDeVencimiento;
    }

    public boolean isEstaVencida() {
        return estaVencida;
    }

    public void setEstaVencida(boolean estaVencida) {
        this.estaVencida = estaVencida;
    }

    public boolean getEstaVencida() {
        if(this.horaEnQueVence.isBefore(LocalDateTime.now())){ this.estaVencida = true; }
        return estaVencida;
    }
    @Override
    public boolean esValida(Heladera heladera_nueva){
        return this.getHeladera().equals(heladera_nueva) && !getEstaVencida();
    }
}
