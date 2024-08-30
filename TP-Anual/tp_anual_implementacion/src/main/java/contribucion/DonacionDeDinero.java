package contribucion;

import colaborador.Colaborador;
import heladera.Heladera;
import sistema.Sistema;

import java.time.LocalDate;

public class DonacionDeDinero extends Contribucion {
    double coeficiente = 0.5;
    private final float monto;
    private Frecuencia frecuencia;

    public DonacionDeDinero(Colaborador colaborador, float monto, Frecuencia frecuencia) {
        this.colaborador = colaborador;
        this.monto = monto;
        this.frecuencia = frecuencia;
        this.fechaDeContribucion = LocalDate.now();
    }

    @Override
    public void procesarLaContribucion() { Sistema.getInstancia().agregarMonto(monto); }
    public double puntosQueSumaColaborador() { return monto * coeficiente; }
}