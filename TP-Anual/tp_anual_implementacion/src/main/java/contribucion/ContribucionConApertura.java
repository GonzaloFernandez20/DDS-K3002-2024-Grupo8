package contribucion;

import heladera.Heladera;

import java.util.List;

public abstract class ContribucionConApertura extends Contribucion{
    protected List<Vianda> viandas;
    protected Heladera heladeraDestino;

    @Override
    public void procesarLaContribucion() {
        heladeraDestino.recibirViandas(viandas);
        for (Vianda vianda : viandas){
            vianda.setEstadoVianda(EstadoVianda.ENTREGADA);
        }
        colaborador.registrarContribucion(this);
    }

    public List<Vianda> getViandas() { return viandas; }
}
