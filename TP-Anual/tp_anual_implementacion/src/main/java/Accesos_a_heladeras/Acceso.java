package Accesos_a_heladeras;

import heladera.Heladera;
import java.time.LocalDateTime;

public class Acceso {
    private Heladera heladera;
    private MotivoApertura motivo;
    private LocalDateTime fecha;


    public Acceso(Heladera heladera, MotivoApertura motivo, LocalDateTime fecha) {
        this.heladera = heladera;
        this.motivo = motivo;
        this.fecha = fecha;
    }
}
