package Accesos_a_heladeras;

import contribucion.Vianda;
import heladera.Heladera;

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
}