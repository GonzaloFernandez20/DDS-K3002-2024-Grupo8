package contribucion;

import colaborador.Colaborador;
import heladera.Heladera;
import nuestras_excepciones.FallaHeladera;
import nuestras_excepciones.ViandaRechazada;

import java.util.List;
import java.time.LocalDate;

public class DistribucionDeVianda extends Contribucion {

    private final Heladera heladeraDeOrigen;
    private final Heladera heladeraDestino;
    private final int cantDeViandas;
    private final MotivoDeDistribucion motivo;

    public DistribucionDeVianda(Colaborador colaborador,
                                LocalDate fechaDeDonacion,
                                Heladera heladeraDeOrigen,
                                Heladera heladeraDestino,
                                int cantDeViandas,
                                MotivoDeDistribucion motivo) {
        super(colaborador, fechaDeDonacion);
        this.heladeraDestino = heladeraDestino;
        this.heladeraDeOrigen = heladeraDeOrigen;
        this.cantDeViandas = cantDeViandas;
        this.motivo = motivo;
    }

    @Override
    public void procesarLaContribucion() {
            List<Vianda> viandas;
            try {
                viandas = heladeraDeOrigen.retirarViandas(cantDeViandas);
            }catch (FallaHeladera e){
                throw new RuntimeException(e);
            }
            viandas.forEach(vianda -> {
                try {
                    vianda.trasladar(heladeraDestino);
                } catch (ViandaRechazada e) {
                    throw new RuntimeException(e);
                }
            });
    }

    public double puntosQueSumaColaborador() {
        double coeficiente = 1;
        return cantDeViandas * coeficiente;
    }

    @Override
    public boolean requieroAcceso() { return true;}
    public Heladera getHeladera() {return heladeraDeOrigen;}
}