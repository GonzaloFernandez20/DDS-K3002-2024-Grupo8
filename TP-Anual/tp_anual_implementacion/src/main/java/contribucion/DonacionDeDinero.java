package contribucion;

import colaborador.Colaborador;
import heladera.Heladera;
import sistema.Sistema;

import java.time.LocalDate;

public class DonacionDeDinero extends Contribucion {
    double coeficiente = 0.5;
    private float monto;
    private Frecuencia frecuencia;

    public DonacionDeDinero(Colaborador colaborador, LocalDate fechaDeDonacion, float monto, Frecuencia frecuencia) {
        super(colaborador, fechaDeDonacion);
        this.monto = monto;
        this.frecuencia = frecuencia;
    }

    @Override
    public void procesarLaContribucion() { Sistema.getInstancia().agregarMonto(monto); }
    public double puntosQueSumaColaborador() { return monto * coeficiente; }
    public boolean requieroAcceso() { return false;}

    @Override
    public Heladera getHeladera() { return null; }
}