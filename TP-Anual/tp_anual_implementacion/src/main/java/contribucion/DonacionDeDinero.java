package contribucion;

import colaborador.Colaborador;
import heladera.Heladera;
import sistema.Sistema;

import java.time.LocalDate;

public class DonacionDeDinero extends Contribucion {
    double coeficiente;
    private float monto;
    private Frecuencia frecuencia;

    public DonacionDeDinero(Colaborador colaborador, LocalDate fechaDeDonacion, float monto, Frecuencia frecuencia) {
        super(colaborador, fechaDeDonacion);
        this.monto = monto;
        this.frecuencia = frecuencia;
        this.coeficiente = 0.5;
    }
    public boolean requieroAcceso() { return false;}

    @Override
    public void procesarContribucion() {
        Sistema.getInstancia().agregarMonto(monto);
    }

    public double puntosQueSumaColaborador() {
        return monto * coeficiente;
    }

    @Override
    public Heladera getHeladera() { return null; }
}