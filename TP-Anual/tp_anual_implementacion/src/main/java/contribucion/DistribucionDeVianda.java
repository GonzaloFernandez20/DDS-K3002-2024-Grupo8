package contribucion;

import colaborador.Colaborador;
import heladera.Heladera;
import heladera.Vianda;
import nuestras_excepciones.FallaHeladera;
import nuestras_excepciones.ViandaRechazada;

import java.util.List;
import java.time.LocalDate;

public class DistribucionDeVianda extends Contribucion {
    private Heladera heladeraDeOrigen;
    private Heladera heladeraDestino;
    private int cantDeViandas;
    private MotivoDeDistribucion motivo;
    double coeficiente;

    public DistribucionDeVianda(Colaborador colaborador, LocalDate fechaDeDonacion, Heladera heladeraDeOrigen, Heladera heladeraDestino, int cantDeViandas, MotivoDeDistribucion motivo) {
        super(colaborador, fechaDeDonacion);
        this.heladeraDestino = heladeraDestino;
        this.heladeraDeOrigen = heladeraDeOrigen;
        this.cantDeViandas = cantDeViandas;
        this.motivo = motivo;
        this.coeficiente = 1;
    }

    @Override
    public boolean requieroAcceso() { return true;}

    public Heladera getHeladera() {return heladeraDeOrigen;}

    @Override
    public void procesarContribucion(){
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
        return cantDeViandas * coeficiente;
    }
}