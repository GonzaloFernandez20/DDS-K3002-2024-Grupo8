package Modelo.Dominio.contribucion;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Excepciones.ExcepcionNoHayEspacioEnDestino;
import Modelo.Excepciones.ExcepcionViandasInsuficientesEnOrigen;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Getter
@Setter
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
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
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
                vianda.trasladar();
            }
            log.info("Se retiraron para traslado {} viandas de la {} ID:{}.",
                    cantidadDeViandasAMover, heladeraDeOrigen.getNombreDelPunto(), heladeraDeOrigen.getid_heladera());

            String viandas = viandasMovidas.stream()
                    .map(Vianda::getTipoDeComida)
                    .collect(Collectors.joining(", "));
            log.info("Viandas retiradas: {}", viandas);
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

        int viandasQueEntranEnDestino =heladeraDestino.espacioDisponible();
        if(viandasQueEntranEnDestino<cantidadDeViandasAMover){
            throw new ExcepcionNoHayEspacioEnDestino("A la heladera destino solo puede mover " + viandasQueEntranEnDestino + " viandas");
        }
    }
    @Override
    public void loggear(){
        log.info("La {} ID:{} recibió {} viandas trasladadas desde la {} ID:{}.",
                heladeraDestino.getNombreDelPunto(), heladeraDestino.getid_heladera(),
                cantidadDeViandasAMover, heladeraDeOrigen.getNombreDelPunto(), heladeraDeOrigen.getid_heladera());

        String viandas = viandasMovidas.stream()
                .map(Vianda::getTipoDeComida) // Obtener el tipo de comida
                .collect(Collectors.joining(", "));
        log.info("Viandas ingresadas: {}", viandas);
    }

    // Getters y Setters -------------------------------------------------------------------------------------------------------------
    @Override
    public List<Vianda> getViandas() {return viandasMovidas;}
    public void setViandas(List<Vianda> viandasMovidas) {this.viandasMovidas = viandasMovidas;}
}
