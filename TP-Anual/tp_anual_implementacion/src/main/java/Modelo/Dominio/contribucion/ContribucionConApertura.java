package Modelo.Dominio.contribucion;

import Modelo.Dominio.heladera.Heladera;
import Modelo.Excepciones.ExcepcionHeladeraLlena;
import jakarta.persistence.*;

import java.util.List;
@Entity
@Table(name = "ContribucionConApertura")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class ContribucionConApertura extends Contribucion{
    @OneToMany
    @JoinColumn(name = "contribucion_con_apertura", referencedColumnName = "id_contribucion")
    protected List<Vianda> viandas;
    @ManyToOne
    @JoinColumn(name = "heladera_destino", referencedColumnName = "id_heladera")
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
