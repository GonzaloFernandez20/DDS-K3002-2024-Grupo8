package contribucion;

import java.time.LocalDate;

import colaborador.Colaborador;
import heladera.Heladera;

public abstract class Contribucion {
    protected Colaborador colaborador;
    protected LocalDate fechaDeDonacion;

    public abstract void procesarContribucion();

    public double puntosQueSumaColaborador() {
        return 0;
    }

    public Contribucion(Colaborador colaborador, LocalDate fechaDeDonacion) {
        this.colaborador = colaborador;
        this.fechaDeDonacion = fechaDeDonacion;
    }

    public boolean requieroAcceso() { return false;}

    public abstract Heladera getHeladera();
}

