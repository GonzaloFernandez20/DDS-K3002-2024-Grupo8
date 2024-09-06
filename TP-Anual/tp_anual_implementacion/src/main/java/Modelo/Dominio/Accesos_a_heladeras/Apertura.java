package Modelo.Dominio.Accesos_a_heladeras;

import Modelo.Dominio.contribucion.Vianda;
import Modelo.Dominio.heladera.Heladera;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Apertura {
    private final Heladera heladera;
    private final MotivoApertura motivo;
    private final LocalDateTime fecha;
    private final List<Vianda> viandasAsociadas;

    public Apertura(Heladera heladera, MotivoApertura motivo, List <Vianda> viandas) {
        this.heladera = heladera;
        this.motivo = motivo;
        this.fecha = LocalDateTime.now();
        this.viandasAsociadas = viandas;
    }
    public boolean aperturaParaEntregaDeDonacionEntre(LocalDate fechaInicio, LocalDate fechaFin){
        return this.getFecha().isAfter(fechaInicio.atStartOfDay()) && this.getFecha().isBefore(fechaFin.atStartOfDay());
    }

    public Heladera getHeladera() {
        return heladera;
    }

    public MotivoApertura getMotivo() {
        return motivo;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public List<Vianda> getViandasAsociadas() {
        return viandasAsociadas;
    }
}