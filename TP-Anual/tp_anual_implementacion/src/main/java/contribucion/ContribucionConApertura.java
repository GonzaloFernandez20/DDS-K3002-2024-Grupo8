package contribucion;

import heladera.Heladera;

import java.util.List;

public abstract class ContribucionConApertura extends Contribucion{
    protected List<Vianda> viandasIngresadas;
    protected Heladera heladeraDestino;

    @Override
    public void procesarLaContribucion() {
        heladeraDestino.recibirViandas(viandasIngresadas);
        for (Vianda vianda : viandasIngresadas){
            vianda.setEstadoVianda(EstadoVianda.ENTREGADA);
        }
        colaborador.registrarContribucion(this);
    }

    public List<Vianda> getViandasIngresadas() { return viandasIngresadas; }
}
