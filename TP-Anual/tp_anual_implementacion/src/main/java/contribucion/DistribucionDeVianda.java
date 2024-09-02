package contribucion;

import colaborador.Colaborador;
import heladera.Heladera;

import java.util.ArrayList;
import java.time.LocalDate;

public class DistribucionDeVianda extends ContribucionConApertura {

    private final Heladera heladeraDeOrigen;
    private final MotivoDeDistribucion motivo;
    private final int cantidadDeViandas;

    public DistribucionDeVianda(Colaborador colaborador,
                                Heladera heladeraDeOrigen,
                                Heladera heladeraDestino,
                                MotivoDeDistribucion motivo,
                                int cantidadDeViandas) {
        this.colaborador = colaborador;
        this.heladeraDestino = heladeraDestino;
        this.heladeraDeOrigen = heladeraDeOrigen;
        this.motivo = motivo;
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

    public double puntosQueSumaColaborador() {
        double coeficiente = 1;
        return viandas.size() * coeficiente;
    }

    public Heladera getHeladera() { return heladeraDeOrigen; }
}