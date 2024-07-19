package colaborador;

import contribucion.Contribucion;
import heladera.Heladera;

import java.time.LocalDate;
import java.time.LocalTime;

public class RegistroAperturaHeladera {
    private Contribucion contribucion;
    private LocalDate inicioDeOperacion;
    private Integer tiempoMaximo = 3;
    private Heladera heladera;

    public void notificarQueSeAcaboElTiempo() {
        // es un observer
    }

    public Heladera getHeladera() {
        return heladera;
    }
}
