package Modelo.Dominio.contribucion;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.sistema.RegistroDeRecaudacion;
import Modelo.Dominio.sistema.Sistema;

import java.time.LocalDate;

public class DonacionDeDinero extends Contribucion {
    private final float monto;
    private Frecuencia frecuencia;

    public DonacionDeDinero(Colaborador colaborador, float monto, Frecuencia frecuencia) {
        this.colaborador = colaborador;
        this.monto = monto;
        this.frecuencia = frecuencia;
        this.fechaDeContribucion = LocalDate.now();
    }

    @Override
    public void procesarLaContribucion() {
        RegistroDeRecaudacion.getInstancia().recibirDinero(monto);
        colaborador.registrarContribucion(this);
    }
    @Override
    public double puntosQueSumaColaborador() {
        double coeficiente = 0.5;
        return monto * coeficiente;
    }
}