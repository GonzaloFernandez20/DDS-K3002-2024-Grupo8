package Accesos_a_heladeras;

import heladera.Heladera;
import java.time.LocalDateTime;

public class Acceso {
    private final Heladera heladera;
    private final MotivoApertura motivo;
    private final LocalDateTime fecha;


    public Acceso(Heladera heladera, MotivoApertura motivo) {
        this.heladera = heladera;
        this.motivo = motivo;
        this.fecha = LocalDateTime.now();
    }
}
