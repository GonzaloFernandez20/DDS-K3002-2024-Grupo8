package Modelo.Dominio.contribucion;

import Modelo.Dominio.Accesos_a_heladeras.Vinculacion;
import Modelo.Dominio.colaborador.Colaborador;


import java.time.LocalDate;

public class RegistroDePersonasEnSituacionVulnerable extends Contribucion {
    private Vinculacion tarjetaEntregada;

    public RegistroDePersonasEnSituacionVulnerable(Colaborador colaborador, Vinculacion tarjetaEntregada, LocalDate fechaDeContribucion) {
        this.tarjetaEntregada = tarjetaEntregada;
        this.colaborador = colaborador;
        this.fechaDeContribucion = fechaDeContribucion;
    }

    @Override
    public void procesarLaContribucion() {
        colaborador.registrarContribucion(this);
    }

    @Override
    public double puntosQueSumaColaborador() {
        double coeficiente = 2;
        return coeficiente;
    }
}
