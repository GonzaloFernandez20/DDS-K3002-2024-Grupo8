package contribucion;

import colaborador.Colaborador;
import heladera.Heladera;
import heladera.Vianda;

import java.util.List;
import java.time.LocalDate;

public class DistribucionDeVianda extends Contribucion {
    private Heladera heladeraDeOrigen;
    private Heladera heladeraDestino;
    private int cantDeViandas;
    private MotivoDeDistribucion motivo;

    public DistribucionDeVianda(Colaborador colaborador, LocalDate fechaDeDonacion, Heladera heladeraDeOrigen, Heladera heladeraDestino, int cantDeViandas, MotivoDeDistribucion motivo) {
        super(colaborador, fechaDeDonacion);
        this.heladeraDestino = heladeraDestino;
        this.heladeraDeOrigen = heladeraDeOrigen;
        this.cantDeViandas = cantDeViandas;
        this.motivo = motivo;
    }

    @Override
    public void procesarLaContribucion() {
        List<Vianda> viandas = heladeraDeOrigen.retirarViandas(cantDeViandas);
        viandas.forEach(vianda -> vianda.trasladar(heladeraDestino));
        heladeraDestino.recibirViandas(viandas);
    }

    @Override
    public double puntosQueSumaColaborador() {
        return cantDeViandas;
    }
}