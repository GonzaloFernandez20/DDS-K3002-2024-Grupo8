package contribucion;

import java.time.LocalDate;

import colaborador.Colaborador;
import heladera.Heladera;
import nuestras_excepciones.ColaboracionInvalida;
import nuestras_excepciones.FallaHeladera;

public abstract class Contribucion {
    protected Colaborador colaborador;
    protected LocalDate fechaDeDonacion;

    public abstract void procesarContribucion() throws ColaboracionInvalida;

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

