package Modelo.Dominio.contribucion;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.heladera.Heladera;

import java.time.LocalDate;
import java.util.List;

public class DonacionDeVianda extends ContribucionConApertura {

    public DonacionDeVianda(Colaborador colaborador, Heladera heladera, List <Vianda> viandas, LocalDate fechaDeContribucion) {
        this.colaborador = colaborador;
        this.fechaDeContribucion = fechaDeContribucion;
        this.heladeraDestino = heladera;
        this.viandas = viandas;
    }
    public void agregarViandaADonacion(Vianda vianda) {
        viandas.add(vianda);
    }

    @Override
    public double puntosQueSumaColaborador() {
        double coeficiente = 1.5;
        return viandas.size() * coeficiente;
    }

    @Override
    public void manejarRechazoViandas() {
        int cantidadQueEntra = heladeraDestino.espacioDisponible();
        viandas = viandas.subList(0, cantidadQueEntra);
        heladeraDestino.recibirViandas(viandas);
        for (Vianda vianda : viandas){vianda.setEstadoVianda(EstadoVianda.ENTREGADA);}
    }


    // ---- Getters y Setters
    public Colaborador getColaborador() {return colaborador;}

}