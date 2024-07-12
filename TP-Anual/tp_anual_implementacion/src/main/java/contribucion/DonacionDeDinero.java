package contribucion;

import colaborador.Colaborador;
import sistema.Sistema;

import java.time.LocalDate;

public class DonacionDeDinero extends Contribucion {
    private float monto;
    private Frecuencia frecuencia;

    public DonacionDeDinero(Colaborador colaborador, LocalDate fechaDeDonacion, float monto, Frecuencia frecuencia) {
        super(colaborador, fechaDeDonacion);
        this.monto = monto;
        this.frecuencia = frecuencia;
    }

    @Override
    public void procesarLaContribucion() {
        Sistema.getInstancia().agregarMonto(monto);
    }

    @Override
    public double puntosQueSumaColaborador() {
        return monto * 0.5;
    }
}