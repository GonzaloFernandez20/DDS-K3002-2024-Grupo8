package Modelo.Dominio.Accesos_a_heladeras;

import Modelo.Dominio.contribucion.ContribucionConApertura;
import Modelo.Dominio.heladera.Heladera;

import java.time.LocalDateTime;

public class PermisoDeApertura {
    private ContribucionConApertura contribucion;
    private Heladera heladera;
    private MotivoApertura motivo;
    private boolean estaVencida;
    private final LocalDateTime horaEnQueVence;

    public PermisoDeApertura(ContribucionConApertura contribucion, Heladera heladera, MotivoApertura motivo) {
        this.contribucion = contribucion;
        this.heladera = heladera;
        this.motivo = motivo;
        this.estaVencida = false;
        this.horaEnQueVence = LocalDateTime.now().plusHours(3);
    }

    public boolean esValida(Heladera heladera){
        return this.heladera.equals(heladera) && !getEstaVencida();
    }

    public boolean getEstaVencida() {
        if(this.horaEnQueVence.isBefore(LocalDateTime.now())){ this.estaVencida = true; }
        return estaVencida;
    }

    // ----------> Getters y Setters
    public ContribucionConApertura getContribucion() {
        return contribucion;
    }
    public Heladera getHeladera() {
        return heladera;
    }
    public MotivoApertura getMotivo() {
        return motivo;
    }
}
