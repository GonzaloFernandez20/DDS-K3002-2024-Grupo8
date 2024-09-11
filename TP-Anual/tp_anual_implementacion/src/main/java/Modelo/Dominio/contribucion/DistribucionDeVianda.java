package Modelo.Dominio.contribucion;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.heladera.Heladera;

import java.util.ArrayList;
import java.time.LocalDate;

public class DistribucionDeVianda extends ContribucionConApertura {

    private final Heladera heladeraDeOrigen;
    private final MotivoDeDistribucion motivoDeDistribucion;
    private final int cantidadDeViandas;

    public DistribucionDeVianda(Colaborador colaborador,
                                Heladera heladeraDeOrigen,
                                Heladera heladeraDestino,
                                MotivoDeDistribucion motivoDeDistribucion,
                                int cantidadDeViandas) {
        this.colaborador = colaborador;
        this.heladeraDestino = heladeraDestino;
        this.heladeraDeOrigen = heladeraDeOrigen;
        this.motivoDeDistribucion = motivoDeDistribucion;
        this.cantidadDeViandas = cantidadDeViandas;
        this.viandas = new ArrayList<>();
        this.fechaDeContribucion = LocalDate.now();
    }

    @Override
    public void procesarLaContribucion() {
        if (viandas.isEmpty()){
            this.viandas = heladeraDeOrigen.retirarViandas(cantidadDeViandas);

            for (Vianda vianda : viandas){
                vianda.setEstadoVianda(EstadoVianda.EN_TRASLADO);
                vianda.trasladar(heladeraDestino);
            }
        }else{
            super.procesarLaContribucion();
        }
    }
    @Override
    public double puntosQueSumaColaborador() {
        double coeficiente = 1;
        return viandas.size() * coeficiente;
    }

    // ---- Getters y Setters
    public Heladera getHeladeraOrigen() { return heladeraDeOrigen; }
    public Colaborador getColaborador() { return colaborador; }
}