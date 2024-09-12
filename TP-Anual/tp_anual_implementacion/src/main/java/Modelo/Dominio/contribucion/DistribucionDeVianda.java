package Modelo.Dominio.contribucion;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.heladera.Heladera;
import org.springframework.cglib.core.Local;


import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;

public class DistribucionDeVianda extends ContribucionConApertura {

    private final Heladera heladeraDeOrigen;


    private final MotivoDeDistribucion motivoDeDistribucion;
    private final Integer cantidadDeViandas;

    public DistribucionDeVianda(Colaborador colaborador,
                                Heladera heladeraDeOrigen,
                                Heladera heladeraDestino,
                                MotivoDeDistribucion motivoDeDistribucion,
                                Integer cantidadDeViandas,
                                LocalDate fechaDeContribucion) {
        this.colaborador = colaborador;
        this.heladeraDestino = heladeraDestino;
        this.heladeraDeOrigen = heladeraDeOrigen;
        this.motivoDeDistribucion = motivoDeDistribucion;
        this.cantidadDeViandas = cantidadDeViandas;
        this.viandas = new ArrayList<>();
        this.fechaDeContribucion = fechaDeContribucion;
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
    public void manejarRechazoViandas() {
        int cantidadQueEntra = heladeraDestino.espacioDisponible();
        List<Vianda> viandasIngresadas = viandas.subList(0, cantidadQueEntra);
        heladeraDestino.recibirViandas(viandasIngresadas);
        for (Vianda vianda : viandasIngresadas){vianda.setEstadoVianda(EstadoVianda.ENTREGADA);}

    }

    @Override
    public double puntosQueSumaColaborador() {
        double coeficiente = 1;
        return viandas.size() * coeficiente;
    }

    // ---- Getters y Setters
    public Heladera getHeladeraOrigen() { return heladeraDeOrigen; }
    public Colaborador getColaborador() { return colaborador; }
    public MotivoDeDistribucion getMotivoDeDistribucion() {return motivoDeDistribucion;}
}
