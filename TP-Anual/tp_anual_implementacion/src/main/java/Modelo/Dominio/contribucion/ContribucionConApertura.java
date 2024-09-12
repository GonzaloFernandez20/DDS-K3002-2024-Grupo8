package Modelo.Dominio.contribucion;

import Modelo.Dominio.heladera.Heladera;
import Modelo.Excepciones.ExcepecionViandasRechazadas;

import java.util.List;

public abstract class ContribucionConApertura extends Contribucion{
    protected List<Vianda> viandas;
    protected Heladera heladeraDestino;

    @Override
    public void procesarLaContribucion() {
        try{
            heladeraDestino.recibirViandas(viandas);
            for (Vianda vianda : viandas){vianda.setEstadoVianda(EstadoVianda.ENTREGADA);}
        }
        catch (ExcepecionViandasRechazadas e){manejarRechazoViandas();}
        finally {
            colaborador.registrarContribucion(this);
        }
    }

    public abstract void manejarRechazoViandas();

    // ---- Getters y Setters
    public List<Vianda> getViandas() { return viandas; }
    public Heladera getHeladeraDestino() { return heladeraDestino; }

}
