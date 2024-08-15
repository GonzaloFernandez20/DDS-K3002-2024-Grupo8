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

    public RegistroAperturaHeladera(Contribucion contribucion, LocalDate inicioDeOperacion, Integer tiempoMaximo, Heladera heladera) {
        this.contribucion = contribucion;
        this.inicioDeOperacion = inicioDeOperacion;
        this.tiempoMaximo = tiempoMaximo;
        this.heladera = heladera;
    }

    public void notificarQueSeAcaboElTiempo() {
        // es un observer
    }

    public Heladera getHeladera() {
        return heladera;
    }

    public Integer getTiempoMaximo() {
        return tiempoMaximo;
    }

    public void setTiempoMaximo(Integer tiempoMaximo) {
        this.tiempoMaximo = tiempoMaximo;
    }
}