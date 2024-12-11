package Modelo.Dominio.contribucion;

import Modelo.Dominio.heladera.Heladera;
import Modelo.Excepciones.ExcepcionHeladeraLlena;
import jakarta.persistence.*;

import java.util.List;
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "contribucion_con_apertura")
public abstract class ContribucionConApertura extends Contribucion{
    @ManyToOne( cascade = CascadeType.PERSIST )
    @JoinColumn(name = "heladera_destino", referencedColumnName = "id_heladera")
    protected Heladera heladeraDestino;

    //Metodos --------------------------------------------------------
    @Override
    public void procesarLaContribucion() {

            for (Vianda vianda : getViandas()){
                heladeraDestino.recibirVianda(vianda);
                vianda.setEstadoVianda(EstadoVianda.ENTREGADA);
            }
            heladeraDestino.movimientoDeViandasFinalizado();
            colaborador.registrarContribucion(this);
        }

    public int cantidadDeViandasInvolucradas(){
        return getViandas().size();
    };


    // Getters y Setters ------------------------------------------------------------------------------
    public Heladera getHeladeraDestino() {return heladeraDestino;}
    public void setHeladeraDestino(Heladera heladeraDestino) {this.heladeraDestino = heladeraDestino;}

    public abstract List<Vianda> getViandas();

}
