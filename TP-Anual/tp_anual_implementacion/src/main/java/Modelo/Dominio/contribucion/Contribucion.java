package Modelo.Dominio.contribucion;

import java.time.LocalDate;

import Modelo.Dominio.colaborador.Colaborador;

public abstract class Contribucion {
    protected Colaborador colaborador;
    protected LocalDate fechaDeContribucion;

    public abstract void procesarLaContribucion();
    public abstract double puntosQueSumaColaborador();
}

