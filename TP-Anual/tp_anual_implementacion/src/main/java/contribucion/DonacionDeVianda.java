package contribucion;

import colaborador.Colaborador;
import heladera.Heladera;

import java.util.ArrayList;
import java.time.LocalDate;

public class DonacionDeVianda extends ContribucionConApertura {

    public DonacionDeVianda(Colaborador colaborador, Heladera heladera) {
        this.colaborador = colaborador;
        this.fechaDeContribucion = LocalDate.now();
        this.heladeraDestino = heladera;
        this.viandas = new ArrayList<>();
    }

    @Override
    public double puntosQueSumaColaborador() {
        double coeficiente = 1.5;
        return viandas.size() * coeficiente;
    }

    public void agregarViandaADonacion(Vianda vianda) {
        viandas.add(vianda);
    }

    // -----------------------------------
    public Heladera getHeladera() {return heladeraDestino;}
}