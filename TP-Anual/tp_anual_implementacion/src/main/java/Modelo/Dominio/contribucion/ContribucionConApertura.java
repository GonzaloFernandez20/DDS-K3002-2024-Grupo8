package Modelo.Dominio.contribucion;

import Modelo.Dominio.heladera.Heladera;
import Modelo.Excepciones.ExcepcionHeladeraLlena;
import jakarta.persistence.*;

import java.util.List;
@MappedSuperclass
public abstract class ContribucionConApertura extends Contribucion{
    @OneToMany(mappedBy = "id_contribucion")
    protected List<Vianda> viandas;
    @ManyToOne
    @JoinColumn(name = "id_heladera_destino")
    protected Heladera heladeraDestino;

    @Override
    public void procesarLaContribucion() {
        try{
            for (Vianda vianda : viandas){
                heladeraDestino.recibirVianda(vianda);
                vianda.setEstadoVianda(EstadoVianda.ENTREGADA);
            }
        }
        catch (ExcepcionHeladeraLlena e){
            manejarViandasQueNoEntraron();
        }
        finally {
            heladeraDestino.movimientoDeViandasFinalizado();
            colaborador.registrarContribucion(this);
        }
    }

    public abstract void manejarViandasQueNoEntraron();

    // ---- Getters y Setters
    public List<Vianda> getViandas() { return viandas; }
    public Heladera getHeladeraDestino() { return heladeraDestino; }

}
