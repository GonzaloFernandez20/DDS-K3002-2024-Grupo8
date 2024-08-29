package contribucion;

import java.time.LocalDate;

import colaborador.Colaborador;

public abstract class Contribucion {
    protected Colaborador colaborador;
    protected LocalDate fechaDeContribucion;

    public abstract void procesarLaContribucion();
    public abstract double puntosQueSumaColaborador();
}

