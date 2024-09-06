package Modelo.Dominio.contribucion;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.heladera.Heladera;

import java.time.LocalDate;
import java.util.List;

public class DonacionDeVianda extends ContribucionConApertura {

    public DonacionDeVianda(Colaborador colaborador, Heladera heladera, List <Vianda> viandas) {
        this.colaborador = colaborador;
        this.fechaDeContribucion = LocalDate.now();
        this.heladeraDestino = heladera;
        this.viandas = viandas;
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
    public Colaborador getColaborador() {return colaborador;}

}