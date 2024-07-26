package colaborador;

import contribucion.Contribucion;
import heladera.Heladera;

import java.time.LocalDate;
import java.time.LocalTime;

public class RegistroAperturaHeladera {
    private Contribucion contribucion;
    private LocalDate inicioDeOperacion;
    private Integer tiempoMaximo;
    private Heladera heladera;

    public RegistroAperturaHeladera(Contribucion contribucion, LocalDate inicioDeOperacion, Heladera heladera) {
        this.contribucion = contribucion;
        this.inicioDeOperacion = inicioDeOperacion;
        this.heladera = heladera;
        this.tiempoMaximo = 3;
    }

    public void notificarQueSeAcaboElTiempo() {
        // es un observer
    }

    public Heladera getHeladera() {
        return heladera;
    }
}
