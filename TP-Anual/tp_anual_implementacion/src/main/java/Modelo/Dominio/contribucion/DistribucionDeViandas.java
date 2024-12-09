package Modelo.Dominio.contribucion;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Excepciones.ExcepcionHeladeraLlena;
import Modelo.Excepciones.ExcepcionNoHayEspacioEnDestino;
import Modelo.Excepciones.ExcepcionViandasInsuficientesEnOrigen;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "DistribucionDeVianda")
public class DistribucionDeViandas extends ContribucionConApertura {
    @ManyToOne( cascade = CascadeType.PERSIST )
    @JoinColumn(name = "heladera_origen", referencedColumnName = "id_heladera")
    private  Heladera heladeraDeOrigen;
    @Enumerated(EnumType.STRING)
    @Column(name = "motivo_de_distribucion")
    private  MotivoDeDistribucion motivoDeDistribucion;
    @Column(name = "cantidad_de_viandas")
    private  Integer cantidadDeViandasAMover;
    @ManyToMany
    @JoinTable(
            name = "vianda_distribuida",
            joinColumns = @JoinColumn(name = "distribucion", referencedColumnName = "id_contribucion"),
            inverseJoinColumns = @JoinColumn(name = "vianda", referencedColumnName = "id_vianda")
    )
    private List<Vianda> viandasMovidas;

    //Constructores ---------------------------------------------------------------------------------------------------
    public DistribucionDeViandas(Colaborador colaborador,
                                 Heladera heladeraDeOrigen,
                                 Heladera heladeraDestino,
                                 MotivoDeDistribucion motivoDeDistribucion,
                                 Integer cantidadDeViandasAMover,
                                 LocalDate fechaDeContribucion) {
        this.colaborador = colaborador;
        this.heladeraDestino = heladeraDestino;
        this.heladeraDeOrigen = heladeraDeOrigen;
        this.motivoDeDistribucion = motivoDeDistribucion;
        this.cantidadDeViandasAMover = cantidadDeViandasAMover;
        this.viandasMovidas = new ArrayList<>();
        this.fechaDeContribucion = fechaDeContribucion;
    }

    public DistribucionDeViandas() {

    }

    //Metodos -----------------------------------------------------
    @Override
    public void procesarLaContribucion() {
        if (viandasMovidas.isEmpty()){
            this.viandasMovidas = heladeraDeOrigen.retirarViandas(cantidadDeViandasAMover);

            for (Vianda vianda : viandasMovidas){
                vianda.trasladar(heladeraDestino);
            }
        }else{
            super.procesarLaContribucion();
        }
    }

    @Override
    public double puntosQueSumaColaborador() {
        double coeficiente = 1;
        return viandasMovidas.size() * coeficiente;
    }

    public void validarSiEsRealizable(){
        int viandasQueHayEnOrigen =heladeraDeOrigen.cantViandasEnStock();
        if(viandasQueHayEnOrigen<cantidadDeViandasAMover){
            throw new ExcepcionViandasInsuficientesEnOrigen("De la heladera origen solo se pueden mover " + viandasQueHayEnOrigen + " viandas");
        }

        int viandasQueEntranEnDestino =heladeraDestino.capacidadRestante();
        if(viandasQueEntranEnDestino<cantidadDeViandasAMover){
            throw new ExcepcionNoHayEspacioEnDestino("A la heladera destino solo puede mover " + viandasQueEntranEnDestino + " viandas");
        }
    }

    // Getters y Setters -------------------------------------------------------------------------------------------------------------
    public Heladera getHeladeraDeOrigen() {return heladeraDeOrigen;}
    public void setHeladeraDeOrigen(Heladera heladeraDeOrigen) {this.heladeraDeOrigen = heladeraDeOrigen;}

    public MotivoDeDistribucion getMotivoDeDistribucion() {return motivoDeDistribucion;}
    public void setMotivoDeDistribucion(MotivoDeDistribucion motivoDeDistribucion) {this.motivoDeDistribucion = motivoDeDistribucion;}

    public Integer getCantidadDeViandasAMover() {return cantidadDeViandasAMover;}
    public void setCantidadDeViandasAMover(Integer cantidadDeViandasAMover) {this.cantidadDeViandasAMover = cantidadDeViandasAMover;}

    @Override
    public List<Vianda> getViandas() {return viandasMovidas;}
    public void setViandas(List<Vianda> viandasMovidas) {this.viandasMovidas = viandasMovidas;}
}
